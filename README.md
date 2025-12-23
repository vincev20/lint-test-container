# Upload Java Artifacts for GitHub Actions (Maven Java CI)

Minimal Maven Java project + GitHub Actions workflow that builds a Java application and uploads the resulting outputs as GitHub Actions artifacts (for easy download from a workflow run).

This repository is intended to be used as a template or reference for Java projects that want a reliable Maven build in CI and a straightforward way to retrieve compiled artifacts (e.g., JARs) from GitHub Actions.

## What’s included

- Maven build (clean + package/verify)
- Optional unit tests (Surefire + JUnit 5, if your project includes tests)
- Upload of build outputs as GitHub Actions artifacts

## Repository structure

- Production code: `src/main/java`
- Unit tests: `src/test/java`
- Maven build output: `target/`

## Quick start (local)

Build the project:

```bash
mvn clean package
```

Run tests (if present):

```bash
mvn test
```

Your packaged artifacts are typically located in:

- `target/*.jar`

## GitHub Actions workflow

The upload workflow lives at:

- `.github/workflows/upload-artifacts.yml`

The workflow builds the project and uploads selected files (commonly JARs from `target/`) as downloadable artifacts.

### Automatic triggers

Most projects run this workflow on:

- `push`
- `pull_request`

### Manual trigger (workflow_dispatch)

If enabled, the workflow can be run manually from the GitHub UI (Actions tab). Useful for:

- rebuilding artifacts on demand
- validating builds without pushing new commits
- generating a fresh artifact bundle for download

## What gets uploaded

What you upload depends on your workflow configuration. Common artifact candidates include:

- `target/*.jar` (primary build output)
- `target/surefire-reports/**` (unit test reports)
- `target/site/**` (if you generate a Maven site)
- any other build outputs your project produces

After the workflow finishes, download artifacts from the workflow run page under “Artifacts”.

## Example workflow snippet

Example job that builds and uploads JARs and test reports:

```yaml
name: Build and Upload (Maven)

on:
  push:
  pull_request:
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: "21"
          cache: maven

      - name: Build with Maven
        run: mvn -B clean package

      - name: Upload build artifacts
        uses: actions/upload-artifact@v4
        with:
          name: java-artifacts
          path: |
            target/*.jar
            target/surefire-reports/**
```

Adjust `java-version`, Maven goals (e.g., `verify`), and uploaded paths as needed.

## Development workflow

- Before committing: `mvn test`
- In CI: `mvn -B clean package` (or `mvn -B clean verify` for stricter validation)

## Notes

- If your build produces multiple JARs (e.g., `original-*.jar` plus a shaded/fat JAR), refine the upload glob to include only what you want.
- GitHub Actions artifacts are for downloading outputs from a workflow run; they are not the same as publishing to GitHub Releases or deploying to a Maven repository.
