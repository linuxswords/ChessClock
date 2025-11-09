# GitHub Actions Workflow

[![Android CI](https://github.com/linuxswords/ChessClock/actions/workflows/android-ci.yml/badge.svg)](https://github.com/linuxswords/ChessClock/actions/workflows/android-ci.yml)

## Workflow: `android-ci.yml`

**Triggers:**
- Push to `main` or `develop`
- Pull requests to `main` or `develop`
- Manual dispatch

**Jobs:**
```
Test (unit tests) ─┐
                   ├─> Build (debug APK)
Lint (checks)     ─┘
```

**Artifacts** (7-day retention):
- `test-reports` - HTML test reports
- `lint-reports` - Lint analysis
- `app-debug` - Debug APK

## Usage

### View Status
```bash
# Using GitHub CLI
make ci-status      # List recent runs
make ci-view        # Open in browser
make ci-logs        # View latest logs

# Or visit
# https://github.com/linuxswords/ChessClock/actions
```

### Download Artifacts
```bash
# Using GitHub CLI
gh run download <run-id>

# Or download from GitHub UI
# Actions → Run → Artifacts section
```

### Run Locally
Replicate CI checks on your machine:
```bash
make ci             # Full pipeline
make test           # Tests only
make lint           # Lint only
make build          # Build only
```

## Troubleshooting

**Tests fail:**
- Download test reports artifact
- Run `make test` locally
- Check test logs in Actions tab

**Lint fails:**
- Download lint reports artifact
- Run `make lint` locally
- Fix issues and push again

**Build fails:**
- Ensure tests and lint pass first
- Run `make build` locally

## Badge

```markdown
[![Android CI](https://github.com/linuxswords/ChessClock/actions/workflows/android-ci.yml/badge.svg)](https://github.com/linuxswords/ChessClock/actions/workflows/android-ci.yml)
```

- 🟢 Green = Passing
- 🔴 Red = Failing
- 🟡 Yellow = Running
