# Test and Lint for Github Actions - Maven Java CI/CD 

A minimal Maven Java project with **Google Java Format linting** and **JaCoCo coverage enforcement (85%+)** for CI/CD readiness (GitHub Actions).

## 🚀 Quick Start

Lint, test, and check coverage (fails if <85%)
mvn clean verify

Auto-format code (Google style)
mvn fmt:format

View coverage report
open target/site/jacoco/index.html


## 🔧 Key Plugins

| Plugin | Purpose | Commands |
|--------|---------|----------|
| [fmt-maven-plugin](https://github.com/spotify/fmt-maven-plugin) | Google Java Format: 2-space indent, braces, imports. Matches PMI style guide. | `mvn fmt:check`<br>`mvn fmt:format`|
| [JaCoCo](https://www.eclemma.org/jacoco/) | 85% line coverage gate (bundle). Reports HTML/XML. | Bound to `verify` |
| JUnit 5 + Surefire | Modern unit tests. | Runs in `test` phase |

## 📋 Workflow
- **Pre-commit**: `mvn fmt:format && mvn test`
- **CI**: `.github/workflows/lint-test.yml` (runs on push/PR).
- **Example**: `App.java` + `AppTest.java` achieve 100% coverage.

Add your code/tests—extend `pom.xml` for deps (e.g., Spring Boot).
