# Chess Clock - Testing Guide

## Overview

This document describes the testing infrastructure for the Chess Clock Android application.

## Test Structure

### Unit Tests
Located in `app/src/test/java/org/linuxswords/games/chessclock/`

- **TimeFormatterTest.java** - Tests time formatting logic with parameterized tests
- **TimeSettingsTest.java** - Tests time settings enum values, labels, and conversions
- **TimeSettingsManagerTest.java** - Tests singleton pattern and settings management
- **ExampleUnitTest.java** - Basic unit test example

### Instrumented Tests
Located in `app/src/androidTest/java/org/linuxswords/games/chessclock/`

- **ExampleInstrumentedTest.java** - Basic Android context test

## Test Framework

The project uses modern testing frameworks:

- **JUnit 5 (Jupiter 5.11.3)** - Modern parameterized testing
- **JUnit 4 (4.13.2)** - Legacy compatibility
- **AssertJ (3.26.3)** - Fluent assertions
- **Espresso (3.6.1)** - Android UI testing
- **AndroidJUnit4** - Android-specific test runner

## Running Tests

### Prerequisites

1. **Java Development Kit (JDK) 17 or higher**
   ```bash
   # Check Java version
   java -version

   # Set JAVA_HOME (example for Linux)
   export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
   ```

2. **Android SDK** (optional for unit tests, required for instrumented tests)
   ```bash
   # Set ANDROID_HOME
   export ANDROID_HOME=$HOME/Android/Sdk
   ```

### Using Makefile (Recommended)

```bash
# Show all available commands
make help

# Check environment setup
make check-env

# Run unit tests
make test

# Run unit tests with verbose output
make test-verbose

# Run instrumented tests (requires device/emulator)
make test-instrumented

# Run all tests
make test-all

# View test reports
make test-report
```

### Using Gradle Directly

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Run all tests
./gradlew test connectedAndroidTest
```

## Test Reports

After running tests, reports are generated at:

- **Unit tests**: `app/build/reports/tests/testDebugUnitTest/index.html`
- **Instrumented tests**: `app/build/reports/androidTests/connected/index.html`

Open these HTML files in a browser to view detailed test results.

## Test Coverage

### Current Coverage

| Component | Tests | Coverage |
|-----------|-------|----------|
| TimeFormatter | ✅ Parameterized | Time formatting logic |
| TimeSettings | ✅ Parameterized | All enum values and methods |
| TimeSettingsManager | ✅ Full | Singleton pattern and state |

### Components to Test (Future)

- **PlayerClock** - Timer logic and increment handling
- **TiltSensor** - Orientation detection (requires instrumented tests)
- **DoubleClickListener** - Gesture detection timing
- **MainActivity** - UI interactions (Espresso tests)
- **SettingsActivity** - Settings selection (Espresso tests)

## Writing New Tests

### Example Unit Test

```java
package org.linuxswords.games.chessclock;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class MyComponentTest {
    @Test
    void testSomething() {
        // Arrange
        MyComponent component = new MyComponent();

        // Act
        String result = component.doSomething();

        // Assert
        assertThat(result).isEqualTo("expected");
    }
}
```

### Example Parameterized Test

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class ParameterizedExampleTest {
    static Stream<Arguments> testData() {
        return Stream.of(
            arguments(input1, expected1),
            arguments(input2, expected2)
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testWithParameters(String input, String expected) {
        assertThat(process(input)).isEqualTo(expected);
    }
}
```

## Continuous Integration

For CI/CD pipelines, use:

```bash
# Complete CI pipeline
make ci

# Or individual steps
make clean
make test-unit
make lint
make build-debug
```

## Troubleshooting

### Issue: "JAVA_HOME is not set"

**Solution**: Install JDK 17+ and set JAVA_HOME
```bash
# Ubuntu/Debian
sudo apt-get install openjdk-17-jdk
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

# Add to ~/.bashrc for persistence
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc
```

### Issue: "SDK location not found"

**Solution**: Install Android SDK or set ANDROID_HOME
```bash
# Option 1: Install Android Studio (includes SDK)
# Option 2: Install SDK command-line tools
export ANDROID_HOME=$HOME/Android/Sdk
```

### Issue: Tests fail with "No connected devices"

**Solution**: Start an emulator or connect a physical device
```bash
# List available emulators
emulator -list-avds

# Start an emulator
emulator -avd <avd-name> &

# Check connected devices
adb devices
```

## Best Practices

1. **Run tests before committing** - Use `make test` or `make ci`
2. **Write tests for new features** - Maintain test coverage
3. **Use parameterized tests** - Reduce code duplication
4. **Use descriptive test names** - Clearly state what is being tested
5. **Follow AAA pattern** - Arrange, Act, Assert
6. **Use AssertJ** - More readable assertions than JUnit's assertEquals

## Integration with Development Workflow

```bash
# Quick development cycle
make dev          # clean + test + build

# Before committing
make ci           # clean + test + lint + build

# Quick iteration (skip tests)
make quick        # fast build only
```
