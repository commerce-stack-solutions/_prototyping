# Commons Module Agents Instructions

The `commons` module provides shared base classes and utilities used across all business modules.

## Key Components

- **`AuditableEntity`**: Interface for entities that need to track creation and modification timestamps.
- **`IdGeneratorProvider` / `GeneratedId`**: Infrastructure for custom ID generation.
- **`ReferenceKey`**: A common pattern for referring to entities via a unique key.
- **Metadata**: Utilities for dynamic enums and mandatory field validation.

## Usage

When creating new entities in other modules, consider implementing `AuditableEntity` if audit trails are required. Use the common data access patterns defined here to maintain consistency.

This module is a `java-library` and should not contain Spring Boot application classes.
