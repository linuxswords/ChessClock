# Chess Clock

[![Android CI](https://github.com/linuxswords/ChessClock/actions/workflows/android-ci.yml/badge.svg)](https://github.com/linuxswords/ChessClock/actions/workflows/android-ci.yml)

Started as a weekend project, trying to build a chess clock app based on tilting the phone :P
inspired by tempest clock.


A chess clock that works by tilting your phone. Put your phone onto something that can be tilted and instead of pressing
a button on your chess clock.

## Features

- **Tilt-based control**: Switch between clocks by tilting your phone
- **Gesture controls**:
  - Single tap to pause
  - Double tap to reset
  - Long press to access settings
- **Multiple time controls**: 3+0, 3+5, 5+0, 5+5, 10+0, 10+5, 15+0, 15+5
- **Increment support**: Fischer chess clock with increment per move
- **Clean UI**: Fullscreen, no buttons on main clock screen

## Development

### Prerequisites

- JDK 17 or higher
- Android SDK (API level 21+)
- Make (optional, for using Makefile)

### Quick Setup

```bash
# Automated setup (Linux)
./setup-dev-env.sh

# Or verify manually
make check-env
```

### Building

```bash
# Using Makefile
make build          # Build debug APK
make build-release  # Build release APK
make install        # Build and install to device

# Using Gradle directly
./gradlew assembleDebug
./gradlew assembleRelease
```

### Testing

```bash
# Using Makefile
make test           # Run unit tests
make test-all       # Run all tests
make lint           # Run lint checks
make ci             # Full CI pipeline

# Using Gradle directly
./gradlew test
./gradlew connectedAndroidTest
```

See [TESTING.md](TESTING.md) for detailed testing documentation.

### Available Commands

Run `make help` to see all available Makefile targets.

## ideas

* [ ] store your last time setting (profile)
* [ ] settings: add sound effects
* [ ] analog clock?
* [ ] simple tap could activate meta info like current time setting/info etc? But I like the single tap pause function
* [ ] show number of moves made

## todos

* [ ] better picture
* [x] show current time setting on front screen
* [x] double tab to reset clock
* [x] avoid flickering/to-quick-switches by lowering sensibility
* [x] implement increment
* [x] fix increment bug at start
* [x] Clock-UI without buttons
* [x] use alpha instead of background change when tilting
* [x] ~~settings: configure tilting threshold~~ figured out a better way to measure tilts
