# Business Extension Module Agents Instructions

The `business-ext` module demonstrates how to extend the core functionality by adding new domains, such as Address management.

## Key Components

- **Address**: Extends the system with address-related entities and controllers.
- **`BusinessExtApplication`**: The main entry point for the extended service.
- **`ExtSampleDataLoader`**: Adds extension-specific sample data.

## CDF-Based Entity Generation

JPA entities in this module are **generated** from CDF (Class Definition Format) JSON files in `src/main/resources/cdf/`. Do **not** edit generated Java files under `src/generated/` directly.

| CDF File                  | Role                                               | Generates               |
|---------------------------|----------------------------------------------------|-------------------------|
| `cdf/AddressEntity.json`  | New entity owned by this module                    | `AddressEntity.java`    |
| `cdf/GroupEntity.json`    | Extension of base `GroupEntity` from business-core | `GroupEntity.java` (merged) |

### CDF Merge Semantics

When this module defines a CDF file for an entity that is **also** defined in a dependency module (e.g., `GroupEntity`), the two definitions are **merged**:

- The dependency's CDF is the **base** (loaded first).
- This module's CDF is the **extension** (applied on top).
- Adding new fields or class-level annotations is allowed.
- Changing an existing field's type is **not** allowed and will fail the build.
- Methods defined in the extension **override** same-named methods in the base.

The `GroupEntity.json` extension adds a `displayOrder` field and a corresponding `toString` override. Because the extension `toString` is only generated as part of the merged class (which always includes all base fields), referencing both base and extension fields in the override is safe and intentional.

## Dependency on Core

This module depends on `business-core`. It inherits all core entities and functionality and can build upon them.

## Extending the System

To add a new extension:
1. Create a CDF file in `src/main/resources/cdf/` for new entities, or an extension CDF for existing entities.
2. Run `./gradlew mergeAndGenerate` to regenerate the Java sources.
3. Create a Repository interface for new entities.
4. Follow the same package structure for consistency (`web`, `dataaccess`, etc.).
