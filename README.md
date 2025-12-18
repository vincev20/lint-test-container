# Test and Lint for GitHub Actions (Maven Java CI)

Minimal Maven Java project configured for CI/CD with:
- Google Java Format linting
- JaCoCo coverage enforcement (85%+)

This repository is intended to be used as a template or reference for Java projects that want consistent formatting, reliable tests, and a coverage gate in CI.

## What’s included

- Formatting/linting via the Spotify `fmt-maven-plugin` (Google Java Format)
- Unit testing with JUnit 5 (via Surefire)
- Coverage reports and coverage gate via JaCoCo (fails the build if coverage is below the threshold)

## Repository structure

- Java production code is in `src/main/java`
- Unit tests are in `src/test/java`

## Quick start (local)

Run lint, tests, and coverage verification (fails if coverage is < 85%):

```bash
mvn clean verify
```

Auto-format code (Google Java Format):

```bash
mvn fmt:format
```

Check formatting without modifying files:

```bash
mvn fmt:check
```

View the JaCoCo HTML coverage report after `mvn verify`:

```bash
open target/site/jacoco/index.html
```

## GitHub Actions usage

The CI workflow lives at:

- `.github/workflows/lint-test.yml`

It is designed to support two usage patterns:
- Manual runs (from the GitHub UI)
- Reuse from another workflow (as a “called” workflow)

### Run manually (workflow_dispatch)

This workflow can be triggered manually from the GitHub UI (Actions tab) when `workflow_dispatch` is enabled in the workflow file.

Typical use cases:
- Validating changes on-demand
- Re-running checks after updating repository secrets/variables
- Trying out changes without pushing new commits

### Call from another workflow (workflow_call)

This workflow can also be invoked from another workflow using `workflow_call`. This is useful for:
- Centralizing CI logic in one place
- Reusing the same lint/test/coverage policy across multiple repositories

Example caller workflow:

```yaml
name: CI (caller)

on:
  push:
  pull_request:

jobs:
  java-ci:
    uses: <OWNER>/<REPO>/.github/workflows/lint-test.yml@<REF>
```

Replace:
- `<OWNER>/<REPO>` with the repository hosting this workflow
- `<REF>` with a branch, tag, or SHA

## Key Maven plugins

| Plugin | Purpose | Common commands |
|-------|---------|-----------------|
| [`fmt-maven-plugin`](https://github.com/spotify/fmt-maven-plugin) | Enforces Google Java Format (imports, braces, whitespace, etc.). | `mvn fmt:check` / `mvn fmt:format` |
| [JaCoCo](https://www.eclemma.org/jacoco/) | Generates coverage reports and enforces a minimum coverage threshold (configured in `pom.xml`). | Runs during `verify` |
| Surefire + JUnit 5 | Runs unit tests. | Runs during `test` |

## Development workflow

- Before committing: `mvn fmt:format && mvn test`
- In CI: `mvn clean verify`

## Notes

- The coverage threshold is enforced during `verify`; adjust it in `pom.xml` if needed.
- Add dependencies in `pom.xml` as your project grows (e.g., Spring Boot, database drivers, test libraries).
