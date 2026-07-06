# Business Core Module Agents Instructions

The `business-core` module implements the fundamental business entities and logic for Organization and Group management.

## Key Components

- **Organization**: Entities, repositories, and controllers for managing organizations. Includes `OrganizationType` handling.
- **Group**: Entities and repositories for managing groups within the system.
- **`BusinessCoreApplication`**: The main entry point for the standalone core service.
- **`CoreSampleDataLoader`**: Initializes the database with sample data for prototyping purposes.

## CDF-Based Entity Generation

JPA entities in this module are **generated** from CDF (Class Definition Format) JSON files located in `src/main/resources/cdf/`. Do **not** edit the generated Java files under `src/generated/` directly – they are overwritten on every build.

| CDF File                     | Generates                    |
|------------------------------|------------------------------|
| `cdf/GroupEntity.json`       | `GroupEntity.java`           |
| `cdf/OrganizationEntity.json`| `OrganizationEntity.java`    |

To modify an entity:
1. Edit its CDF file in `src/main/resources/cdf/`.
2. Run `./gradlew mergeAndGenerate` (or just `./gradlew build`) to regenerate.

## Extension Pattern

This module is designed to be used as a dependency for extension modules like `business-ext`. It produces both a regular JAR and an executable Boot JAR.

When adding new core features:
1. Define the JPA entity in a CDF file under `src/main/resources/cdf/`.
2. Create a Repository interface.
3. Expose functionality via a REST controller in `web`.
