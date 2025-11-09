# GitHub Actions CI/CD Workflows

This directory contains the automated workflows for the Chess Clock project.

## Workflows

### Android CI (`android-ci.yml`)

**Status**: [![Android CI](https://github.com/linuxswords/ChessClock/actions/workflows/android-ci.yml/badge.svg)](https://github.com/linuxswords/ChessClock/actions/workflows/android-ci.yml)

#### Trigger Events

This workflow runs automatically on:
- **Push** to `main` or `develop` branches
- **Pull requests** targeting `main` or `develop` branches
- **Manual trigger** via GitHub UI (workflow_dispatch)

#### Jobs

The workflow consists of three jobs that run in sequence:

##### 1. Test Job
- **Purpose**: Run all unit tests
- **Runner**: Ubuntu Latest
- **Steps**:
  1. Checkout code
  2. Set up JDK 17 (Temurin distribution)
  3. Cache Gradle dependencies
  4. Grant execute permissions to gradlew
  5. Run unit tests with Gradle
  6. Upload test reports (always, even on failure)
  7. Upload test results (always, even on failure)

**Artifacts Generated**:
- `test-reports` - HTML test reports (7-day retention)
- `test-results` - XML test results (7-day retention)

##### 2. Lint Job
- **Purpose**: Run Android lint checks
- **Runner**: Ubuntu Latest
- **Steps**:
  1. Checkout code
  2. Set up JDK 17 (Temurin distribution)
  3. Cache Gradle dependencies
  4. Grant execute permissions to gradlew
  5. Run lint checks with Gradle
  6. Upload lint reports (always, even on failure)

**Artifacts Generated**:
- `lint-reports` - Lint analysis HTML reports (7-day retention)

##### 3. Build Job
- **Purpose**: Build debug APK
- **Dependencies**: Requires `test` and `lint` jobs to pass
- **Runner**: Ubuntu Latest
- **Steps**:
  1. Checkout code
  2. Set up JDK 17 (Temurin distribution)
  3. Cache Gradle dependencies
  4. Grant execute permissions to gradlew
  5. Build debug APK
  6. Upload APK artifact
  7. Display APK size and path

**Artifacts Generated**:
- `app-debug` - Debug APK file (7-day retention)

#### Workflow Features

1. **Gradle Caching**: Dependencies are cached to speed up builds
2. **Parallel Execution**: Test and lint jobs run in parallel
3. **Fail-Fast**: Build job only runs if tests and lint pass
4. **Artifact Upload**: Test reports, lint results, and APK are preserved
5. **Always Upload Reports**: Test/lint reports upload even on failure for debugging
6. **No Daemon**: Uses `--no-daemon` for CI reliability

#### Viewing Results

##### In GitHub UI

1. Go to the [Actions tab](https://github.com/linuxswords/ChessClock/actions)
2. Click on a workflow run
3. View job status and logs
4. Download artifacts from the "Artifacts" section

##### Accessing Artifacts

After workflow completion, artifacts are available for download:

```bash
# Download using GitHub CLI
gh run download <run-id>

# Or download from GitHub UI
# Actions → Workflow Run → Artifacts section
```

##### Test Reports

1. Download the `test-reports` artifact
2. Extract and open `index.html` in a browser
3. View detailed test results, including:
   - Test pass/fail status
   - Execution time
   - Stack traces for failures

##### APK Installation

1. Download the `app-debug` artifact
2. Install on device:
   ```bash
   adb install app-debug.apk
   ```

#### Environment Variables

The workflow uses these environment configurations:

| Variable | Value | Purpose |
|----------|-------|---------|
| `JAVA_VERSION` | 17 | Required for Android build |
| `JAVA_DISTRIBUTION` | temurin | OpenJDK distribution |
| `GRADLE_OPTS` | `--no-daemon` | CI-optimized Gradle settings |

#### Troubleshooting

##### Workflow Fails on Test Job

1. Check test reports in artifacts
2. Run tests locally: `make test`
3. Review test logs in the workflow run

##### Workflow Fails on Lint Job

1. Check lint reports in artifacts
2. Run lint locally: `make lint`
3. Fix lint issues and push again

##### Workflow Fails on Build Job

1. Check if tests/lint passed (build depends on them)
2. Review build logs in the workflow run
3. Try building locally: `make build`

##### Gradle Cache Issues

If builds are slower than expected:
- GitHub automatically caches Gradle dependencies
- Cache is invalidated when `build.gradle` changes
- First run after cache invalidation will be slower

#### Manual Workflow Trigger

You can manually trigger the workflow:

1. Go to Actions tab
2. Select "Android CI" workflow
3. Click "Run workflow"
4. Select branch
5. Click "Run workflow" button

#### Future Enhancements

Planned improvements for the CI/CD pipeline:

- [ ] Add instrumented tests on emulator
- [ ] Code coverage reporting (JaCoCo)
- [ ] Automatic release builds on tags
- [ ] Deploy to Google Play (internal track)
- [ ] Performance testing
- [ ] Security scanning (dependency check)
- [ ] Automated changelog generation

## Local Development

To replicate CI checks locally:

```bash
# Run the same checks as CI
make ci

# Or individual steps
make clean
make test
make lint
make build
```

## Badge Status

Add this badge to any Markdown file to show workflow status:

```markdown
[![Android CI](https://github.com/linuxswords/ChessClock/actions/workflows/android-ci.yml/badge.svg)](https://github.com/linuxswords/ChessClock/actions/workflows/android-ci.yml)
```

The badge shows:
- **Green "passing"**: All jobs succeeded
- **Red "failing"**: One or more jobs failed
- **Yellow "in progress"**: Workflow is running

## Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [setup-java Action](https://github.com/actions/setup-java)
- [upload-artifact Action](https://github.com/actions/upload-artifact)
- [Android Gradle Plugin](https://developer.android.com/build)
