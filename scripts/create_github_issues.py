#!/usr/bin/env python3
import sys
import re
import os
import subprocess
import time

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

def main():
    mode = "--dry-run"
    if len(sys.argv) > 1 and sys.argv[1] == "--live":
        mode = "--live"

    cat1 = "docs/issues/catalog-part-1.md"
    cat2 = "docs/issues/catalog-part-2.md"

    issues = parse_catalog(cat1) + parse_catalog(cat2)
    print("=" * 65)
    print(f" Veridian Issue Creator - Mode: {mode}")
    print(f" Total Issues Parsed: {len(issues)}")
    print("=" * 65)

    for issue in issues:
        num = issue["number"]
        title = issue["title"]
        service = issue["service"]
        labels = ",".join(issue["labels"])
        desc = issue["description"]
        ac = issue["acceptance_criteria"]

        body = f"## Service\n`{service}`\n\n## Description\n{desc}\n\n## Acceptance Criteria\n{ac}\n"

        if mode == "--live":
            print(f"Creating issue #{num}: {title}...")
            cmd = ["gh", "issue", "create", "--title", title, "--body", body]
            if labels:
                cmd.extend(["--label", labels])
            try:
                subprocess.run(cmd, check=True)
                time.sleep(1) # rate limiting protection
            except Exception as e:
                print(f"Failed to create #{num}: {e}")
        else:
            print(f"[DRY-RUN] #{num:03d}: {title} [{labels}]")

    print("=" * 65)
    print(f" Completed processing {len(issues)} issues.")
    print("=" * 65)

if __name__ == "__main__":
    main()
