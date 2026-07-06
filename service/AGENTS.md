# Service Module Agents Instructions

This directory contains the Gradle root project for all Spring Boot modules.

## Build and Run

- **Build everything**: `./gradlew build`
- **Generate sources only**: `./gradlew mergeAndGenerate`
- **Run tests**: `./gradlew test`
- **Run a specific module**: `./gradlew :<module-name>:bootRun` (e.g., `./gradlew :business-ext:bootRun`)

## CDF Code Generation

JPA entities are defined as JSON files in `src/main/resources/cdf/` inside each module and generated into `src/generated/main/java/` (git-ignored). The `mergeAndGenerate` task runs automatically before `compileJava`.

See `buildSrc/` for the Gradle plugin implementation (`io.commercestacksolutions.cdf-codegen`).

## Gradle Configuration

- The root `build.gradle.kts` defines the Spring Boot and Dependency Management plugin versions.
- Java 25 toolchain is configured for all projects.
- Shared group and version are defined here.
- `buildSrc/` contains the custom CDF code-generation Gradle plugin.

## Adding New Modules

1. Create a new directory.
2. Add it to `settings.gradle.kts`.
3. Create a `build.gradle.kts` for the new module (apply `id("io.commercestacksolutions.cdf-codegen")` for entity generation).
4. If it's a Spring Boot application, ensure it follows the pattern seen in `business-core` or `business-ext`.
5. Place CDF JSON files in `src/main/resources/cdf/`.
