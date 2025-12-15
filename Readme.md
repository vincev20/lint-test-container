# Maven Java SonarQube CI Workflow

Reusable GitHub Actions workflow for building Maven projects with SonarQube analysis on self-hosted runners. 

## Overview

This repository provides a callable GitHub Actions workflow (`build.yml`) that automates Maven builds (`mvn verify`) and SonarQube code analysis. It triggers on pushes to `main`, pull requests (opened/synchronize/reopened), or calls from other workflows. Caching optimizes SonarQube and Maven dependencies. After execution, detailed code quality reports, including bugs, vulnerabilities, and coverage, appear in your SonarQube dashboard.

## Prerequisites

- Self-hosted GitHub runner configured with JDK 11, Maven, and SonarQube scanner.
- SonarQube server accessible via URL.
- Repository secrets: `SONAR_TOKEN` and `SONAR_HOST_URL`. `GITHUB_TOKEN` is automatic.

## Setup Secrets

Add secrets in repo **Settings > Secrets and variables > Actions > New repository secret**. 

| Secret Name     | Value Description                          |
|-----------------|--------------------------------------------|
| SONAR_HOST_URL | Your SonarQube server URL (e.g., `https://sonarqube.example.com`) |
| SONAR_TOKEN    | User token from SonarQube (generate below)  |

## Generate SonarQube Token

Log into SonarQube > **My Account > Security > Generate Tokens > User Token**. Enter name (e.g., "GitHub Actions"), set expiry, generate, and copy the token immediately.  

## Workflow Breakdown

The workflow scans a Java Maven app for quality issues via SonarQube.


**How it works**: Checks out code, sets up Java, caches tools, builds/tests the Maven project (`verify`), executes SonarQube scanner to analyze for bugs/duplications/coverage/security, and uploads report. `GITHUB_TOKEN` allows PR comments/status checks. View full report post-run in SonarQube UI (project dashboard).

## Usage

**Automatic**: Push/PR triggers analysis.

**Call externally**:
Same Repo
jobs:
  trigger-build:
    uses: ./.github/workflows/build.yml@main  # Relative path, current main branch
    secrets: inherit

Cross Repo
jobs:
  trigger-build:
    uses: your-org/target-repo/.github/workflows/build.yml@main
    secrets:
      inherit  # Or specific: SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}

