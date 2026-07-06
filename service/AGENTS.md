# Service Module Agents Instructions

This directory contains the Gradle root project for all Spring Boot modules.

## Build and Run

- **Build everything**: `./gradlew build`
- **Run tests**: `./gradlew test`
- **Run a specific module**: `./gradlew :<module-name>:bootRun` (e.g., `./gradlew :business-ext:bootRun`)

## Gradle Configuration

- The root `build.gradle.kts` defines the Spring Boot and Dependency Management plugin versions.
- Java 21 toolchain is configured for all projects.
- Shared group and version are defined here.

## Adding New Modules

1. Create a new directory.
2. Add it to `settings.gradle.kts`.
3. Create a `build.gradle.kts` for the new module.
4. If it's a Spring Boot application, ensure it follows the pattern seen in `business-core` or `business-ext`.
