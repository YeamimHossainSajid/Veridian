#!/usr/bin/env python3
import sys
import re
import os
import subprocess
import time
import json
import urllib.request
import urllib.error

def parse_catalog(filepath):
    issues = []
    if not os.path.exists(filepath):
        print(f"Warning: {filepath} not found")
        return issues

    current_issue = None
    with open(filepath, 'r', encoding='utf-8') as f:
        for line in f:
            line_str = line.strip()
            match_header = re.match(r"^###\s+Issue\s+#(\d+):\s+(.+)$", line_str)
            if match_header:
                if current_issue:
                    issues.append(current_issue)
                current_issue = {
                    "number": int(match_header.group(1)),
                    "title": match_header.group(2).strip(),
                    "service": "",
                    "labels": [],
                    "description": "",
                    "acceptance_criteria": ""
                }
                continue

            if not current_issue:
                continue

            if line_str.startswith("- **Service**:"):
                val = line_str.replace("- **Service**:", "").strip().strip("`")
                current_issue["service"] = val
            elif line_str.startswith("- **Labels**:"):
                raw_labels = line_str.replace("- **Labels**:", "").strip()
                labels = [l.strip().strip("`") for l in raw_labels.split(",") if l.strip()]
                current_issue["labels"] = labels
            elif line_str.startswith("- **Description**:"):
                current_issue["description"] = line_str.replace("- **Description**:", "").strip()
            elif line_str.startswith("- **Acceptance Criteria**:"):
                current_issue["acceptance_criteria"] = line_str.replace("- **Acceptance Criteria**:", "").strip()

        if current_issue:
            issues.append(current_issue)

    return issues

def get_token():
    res = subprocess.run(["gh", "auth", "token"], capture_output=True, text=True, check=True)
    return res.stdout.strip()

def main():
    mode = "--dry-run"
    if len(sys.argv) > 1 and sys.argv[1] == "--live":
        mode = "--live"

    cat1 = "docs/issues/catalog-part-1.md"
    cat2 = "docs/issues/catalog-part-2.md"

    issues = parse_catalog(cat1) + parse_catalog(cat2)
    print("=" * 65)
    print(f" Veridian Issue Creator - Mode: {mode}")
    print(f" Total Issues to Process: {len(issues)}")
    print("=" * 65)

    if mode != "--live":
        for issue in issues:
            print(f"[DRY-RUN] #{issue['number']:03d}: {issue['title']} [{', '.join(issue['labels'])}]")
        print("=" * 65)
        print(" Dry run complete. Run with --live to push to GitHub.")
        print("=" * 65)
        return

    token = get_token()
    headers = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/vnd.github+json",
        "User-Agent": "Veridian-Issue-Creator"
    }

    url = "https://api.github.com/repos/YeamimHossainSajid/Veridian/issues"

    created_count = 0
    for issue in issues:
        num = issue["number"]
        title = issue["title"]
        service = issue["service"]
        labels = issue["labels"]
        desc = issue["description"]
        ac = issue["acceptance_criteria"]

        body = (
            f"### Service\n`{service}`\n\n"
            f"### Description\n{desc}\n\n"
            f"### Acceptance Criteria\n{ac}\n\n"
            f"---\n*Part of the Veridian High-Frequency Trading Architecture Roadmap.*"
        )

        payload = json.dumps({
            "title": f"#{num}: {title}",
            "body": body,
            "labels": labels
        }).encode("utf-8")

        success = False
        attempts = 0
        while not success and attempts < 5:
            attempts += 1
            try:
                req = urllib.request.Request(url, data=payload, headers=headers, method="POST")
                with urllib.request.urlopen(req) as resp:
                    resp_data = json.loads(resp.read().decode())
                    gh_num = resp_data.get("number")
                    gh_url = resp_data.get("html_url")
                    print(f"✓ [{num:03d}/200] Created GitHub Issue #{gh_num}: {title} -> {gh_url}", flush=True)
                    created_count += 1
                    success = True
                # Delay to prevent secondary rate limiting
                time.sleep(1.0)
            except urllib.error.HTTPError as e:
                err_body = e.read().decode()
                if e.code == 403 and "secondary rate limit" in err_body.lower():
                    print(f"⚠ Hit secondary rate limit on #{num}. Waiting 40s before retry...", flush=True)
                    time.sleep(40)
                else:
                    print(f"✗ Failed to create #{num} (HTTP {e.code}): {err_body}", flush=True)
                    time.sleep(2)
            except Exception as ex:
                print(f"✗ Network error on #{num}: {ex}. Retrying in 5s...", flush=True)
                time.sleep(5)

    print("=" * 65)
    print(f" Completed! Created {created_count} issues on GitHub.")
    print("=" * 65)

if __name__ == "__main__":
    main()
