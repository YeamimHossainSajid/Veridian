#!/usr/bin/env python3
import urllib.request
import json
import subprocess
import time

def get_token():
    res = subprocess.run(["gh", "auth", "token"], capture_output=True, text=True, check=True)
    return res.stdout.strip()

def main():
    token = get_token()
    headers = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/vnd.github+json",
        "User-Agent": "Veridian-Issue-Creator"
    }

    # Fetch existing labels
    req = urllib.request.Request("https://api.github.com/repos/YeamimHossainSajid/Veridian/labels?per_page=100", headers=headers)
    with urllib.request.urlopen(req) as resp:
        existing = json.loads(resp.read().decode())
    existing_names = {l["name"].lower() for l in existing}

    from create_github_issues import parse_catalog
    issues = parse_catalog("docs/issues/catalog-part-1.md") + parse_catalog("docs/issues/catalog-part-2.md")
    all_labels = set()
    for iss in issues:
        all_labels.update(iss["labels"])

    print(f"Total required labels: {len(all_labels)}")
    print(f"Existing labels: {len(existing_names)}")

    colors = {
        "good-first-issue": "7057ff",
        "beginner": "008672",
        "level-2": "1d76db",
        "intermediate": "fbca04",
        "advanced": "d93f0b",
        "hft-specialist": "b60205",
        "documentation": "0075ca",
        "bug": "d73a4a",
        "enhancement": "a2eeef",
        "testing": "c2e0c6",
        "performance": "e99695",
        "security": "5319e7",
        "low-latency": "b60205",
        "concurrency": "f9d0c4",
        "kafka": "000000",
        "redis": "dc382d",
        "clickhouse": "ffcc00",
        "cassandra": "1f88c0",
        "docker": "2496ed",
        "kubernetes": "326ce5",
        "istio": "466bb0",
        "argocd": "ef6b48",
        "observability": "fef2c0",
        "metrics": "c5def5",
        "tracing": "d4c5f9",
        "api": "bfd4f2",
        "database": "bfdadc",
        "finance": "0e8a16",
        "trading": "0052cc",
        "risk": "b60205",
        "matching": "d93f0b",
    }

    created = 0
    for label in sorted(list(all_labels)):
        if label.lower() in existing_names:
            continue
        color = colors.get(label, "cfd3d7")
        payload = json.dumps({"name": label, "color": color}).encode("utf-8")
        post_req = urllib.request.Request("https://api.github.com/repos/YeamimHossainSajid/Veridian/labels", data=payload, headers=headers, method="POST")
        try:
            with urllib.request.urlopen(post_req) as resp:
                print(f"Created label: {label} (#{color})")
                created += 1
            time.sleep(0.1)
        except Exception as e:
            print(f"Error creating label {label}: {e}")

    print(f"Labels setup complete. Created {created} new labels.")

if __name__ == "__main__":
    main()
