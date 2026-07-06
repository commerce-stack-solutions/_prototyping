# Root Agents Instructions

Welcome to the **Prototyping** repository. This project is a multi-module Spring Boot application designed for rapid prototyping of commerce-related services.

## General Guidelines

- **Architecture**: The project follows a modular architecture. `commons` is the base, `business-core` provides core features, and `business-ext` extends them.
- **Java Version**: Always use Java 21 features where appropriate.
- **Dependencies**: Manage dependencies in the root `service/build.gradle.kts` and individual module `build.gradle.kts` files.
- **Prototyping**: Since this is a prototyping repo, prioritize simplicity and readability while maintaining a clean separation of concerns.

## Module Overview

- `service/commons`: Shared components.
- `service/business-core`: Core business entities (Organization, Group).
- `service/business-ext`: Extensions (Address).

Refer to the specific `AGENTS.md` in each directory for more localized instructions.
