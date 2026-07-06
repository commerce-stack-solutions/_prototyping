# Business Core Module Agents Instructions

The `business-core` module implements the fundamental business entities and logic for Organization and Group management.

## Key Components

- **Organization**: Entities, repositories, and controllers for managing organizations. Includes `OrganizationType` handling.
- **Group**: Entities and repositories for managing groups within the system.
- **`BusinessCoreApplication`**: The main entry point for the standalone core service.
- **`CoreSampleDataLoader`**: Initializes the database with sample data for prototyping purposes.

## Extension Pattern

This module is designed to be used as a dependency for extension modules like `business-ext`. It produces both a regular JAR and an executable Boot JAR.

When adding new core features:
1. Define the JPA entity in `dataaccess`.
2. Create a Repository interface.
3. Expose functionality via a REST controller in `web`.
