# Prototyping Project

This repository serves as a workspace for creating simplified and isolated prototypes for the Commerce Stack Solutions ecosystem. It is structured as a multi-module Spring Boot application.

## Project Structure

The project is divided into several modules under the `service/` directory:

- **`commons`**: Contains shared utilities, base interfaces (like `AuditableEntity`), and infrastructure-related code such as ID generation and metadata handling.
- **`business-core`**: Implements the core business logic, focusing on Organization and Group management. It provides REST controllers and JPA repositories for these entities.
- **`business-ext`**: Demonstrates how to extend the core functionality. It currently includes Address management and depends on the `business-core` module.

## Technologies Used

- **Java 21**: Leveraging modern Java features.
- **Spring Boot 4.0.7**: Providing the foundation for the microservices.
- **Gradle**: Used for build automation and dependency management.
- **JPA / Hibernate**: For data persistence.
- **H2 Database**: An in-memory database used for prototyping.

## Getting Started

To build the project, navigate to the `service/` directory and run:

```bash
./gradlew build
```

Each module (except `commons`) can be run as a standalone Spring Boot application.
