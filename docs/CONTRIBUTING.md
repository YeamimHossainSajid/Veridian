# Contributing to Veridian

Thank you for your interest in contributing to **Veridian – TradeCraft Pro**! As an institutional-grade trading platform, code quality, low-latency performance, and test rigor are paramount.

---

## Getting Started

1. Fork the repository and clone your fork locally.
2. Create a topic branch: `git checkout -b feature/my-feature`.
3. Check our [Issue Backlog](../../ISSUES.md) for available tasks (labeled by difficulty level from Beginner to Advanced).

---

## Coding Standards

- **Zero Allocation on Hot Paths**: Code executing within `matching-engine` or `risk-engine` inner loops must avoid heap object allocation. Avoid stream allocations, boxing/unboxing, and excessive string concatenations.
- **Strict Formatting**: Follow `.editorconfig` (4-space indent for Java, 2-space for YAML).
- **Unit Tests**: Every new feature or bugfix must be accompanied by JUnit 5 tests.
- **Git Commit Hygiene**: Follow Conventional Commits format:
  - `feat(matching-engine): ...`
  - `fix(order-service): ...`
  - `docs: ...`
  - `test: ...`

---

## Submitting Pull Requests

1. Run `./gradlew check` to verify compilation and test suites pass.
2. Push your branch and open a PR against `main`.
3. Reference the relevant issue number in the PR description (e.g., `Resolves #42`).
