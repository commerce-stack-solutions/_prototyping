# Business Extension Module Agents Instructions

The `business-ext` module demonstrates how to extend the core functionality by adding new domains, such as Address management.

## Key Components

- **Address**: Extends the system with address-related entities and controllers.
- **`BusinessExtApplication`**: The main entry point for the extended service.
- **`ExtSampleDataLoader`**: Adds extension-specific sample data.

## Dependency on Core

This module depends on `business-core`. It inherits all core entities and functionality and can build upon them.

## Extending the System

To add a new extension:
- Ensure the core dependency is present in `build.gradle.kts`.
- Follow the same package structure for consistency (`web`, `dataaccess`, etc.).
- Use `CoreSampleDataLoader` as a reference for how to seed data that might depend on core entities.
