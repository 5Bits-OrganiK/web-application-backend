# OrganiK Platform (web-application-backend)

## Overview

OrganiK Platform is the Spring Boot backend of OrganiK, a web application that helps minimarkets of organic products control their inventory, expiration dates, supply and conservation conditions, and connects them with their suppliers. The project is structured around Domain-Driven Design (DDD), organized into bounded contexts (`iam`, `inventory`, `suppliers`, `requisition`, `procurements`, `conservation` and `shared`), using application-layer command/query contracts, a shared `Result` abstraction for command outcomes, and JPA persistence.

The backend serves the OrganiK web application ([organik-website-v2](https://github.com/5Bits-OrganiK/organik-website-v2)). It is built incrementally: the `iam` and `inventory` bounded contexts are complete up to their REST APIs, while the other bounded contexts currently contain their domain model and persistence mapping.

## Features

- List users scoped to a minimarket
- Retrieve a user by its identifier
- Retrieve a user by email address
- Create (persist) a new user with a pending invitation
- Register stock lots and list them by minimarket, flagging lots at or below their minimum stock
- Register inventory movements (entry, exit, adjustment and waste) on a stock lot and list them by lot
- Create offers for a stock lot that end on or before its expiration date, and list them by lot
- Domain model (aggregates, value objects, commands and JPA converters) for suppliers and their offered products, supply requests, shipment orders and storage conservation
- Map command and query outcomes to HTTP responses through interface-layer response translators
- Custom Hibernate naming strategy to convert identifiers to snake_case and plural table names

## Bounded contexts

| Bounded context | Responsibility                                      | Current state                                    |
|-----------------|-----------------------------------------------------|--------------------------------------------------|
| `iam`           | Users, roles and invitation status                  | Domain, application and REST API                 |
| `inventory`     | Stock lots, inventory movements and offers          | Domain, application and REST API                 |
| `suppliers`     | Suppliers and the products they offer               | Domain and persistence mapping                   |
| `requisition`   | Supply requests shared by minimarkets               | Domain and persistence mapping                   |
| `procurements`  | Shipment orders and their acceptance or rejection   | Domain and persistence mapping                   |
| `conservation`  | Storage zones and temperature and humidity readings | Domain and persistence mapping                   |
| `shared`        | `Result` abstraction and the naming strategy        | Complete                                         |

## Technologies

- OpenJDK 27 and Spring Boot 4.1.1
- Apache Maven 3.10.0 (through the Maven wrapper)
- Spring Web (REST controllers)
- Spring Data JPA (Hibernate) with MySQL
- Spring Validation (Jakarta Bean Validation)
- SpringDoc OpenAPI 3 / Swagger UI
- Lombok (compile-time helpers)
- PlantUML (architecture diagrams in `docs/`)

## Prerequisites

- OpenJDK 27
- Apache Maven 3.10.0 (or the included `mvnw` wrapper)
- MySQL 8+ (database server)

## Environment variables

The application resolves credentials and connection details from environment variables. Set the following before running locally:

| Variable            | Description                                             |
|---------------------|---------------------------------------------------------|
| `DATABASE_USER`     | MySQL username                                          |
| `DATABASE_PASSWORD` | MySQL password                                          |
| `DATABASE_URL`      | Database host / hostname used by the production profile |
| `DATABASE_NAME`     | Database name used by the production profile            |
| `DATABASE_PORT`     | Database port used by the production profile            |

The default and `dev` profiles use the `organik-db` database on `localhost:3306`, which is created automatically when it does not exist.

## Spring profiles

| Profile     | Usage                                                                |
|-------------|----------------------------------------------------------------------|
| *(default)* | Uses `localhost:3306` with SSL disabled; SQL logging on              |
| `dev`       | Uses `localhost:3306` with SSL disabled; SQL logging on              |
| `prod`      | Builds the JDBC URL from env vars with SSL required; SQL logging off |

Set the active profile with:

```bash
export SPRING_PROFILES_ACTIVE=dev
```

## Technical stories

The API-focused technical stories for frontend integration are in [`docs/user-stories.md`](docs/user-stories.md).

## Class diagram

A PlantUML class diagram that reflects the code structure and bounded contexts is available at [`docs/class-diagram.puml`](docs/class-diagram.puml).

## Architecture Decision Records

Significant architecture decisions and their trade-offs are recorded in [`docs/adrs.md`](docs/adrs.md).

## Architecture notes

- `iam.application.commandservices` and `iam.application.queryservices` define the application-layer service contracts
- `iam.application.internal.commandservices` and `iam.application.internal.queryservices` contain the implementations
- `shared.application.result.Result` models command outcomes without introducing HTTP or domain concepts
- `iam.interfaces.rest.transform.ResponseEntityFromUserCommandResultAssembler` maps command results to `ResponseEntity` values for the REST boundary
- `iam.interfaces.rest.transform.ResponseEntityFromUserQueryResultAssembler` maps query results and localized query errors to `ResponseEntity` values for the REST boundary

## API documentation (Swagger UI)

When the application is running, interactive API documentation is available at:

```
http://localhost:8080/swagger-ui/index.html
```

## Error responses

- Invalid query parameters (`GET /api/v1/users`) return `400 Bad Request` with a localized `ProblemDetail` payload.
- Invalid request bodies (`POST /api/v1/users`) return `400 Bad Request`.
- Duplicate user creation returns `409 Conflict` with a localized `ProblemDetail` payload.
- Not-found lookups (`GET /api/v1/users/{id}` and `GET /api/v1/users?email=...`) return `404 Not Found` with a localized `ProblemDetail` payload.
- Inventory endpoints (`/api/v1/stock-lots`, `/api/v1/inventory-movements` and `/api/v1/offers`) return `400 Bad Request` for invalid bodies or query parameters and `404 Not Found` for unknown identifiers or stock lots.
- Registering a stock lot with a lot code already used by the minimarket returns `409 Conflict`.
- An exit or waste movement larger than the units in the stock lot returns `409 Conflict`.
- An offer that ends after the expiration date of its stock lot returns `409 Conflict`.

## Getting started (quick)

Install the prerequisites, ensure MySQL is running locally, and export the database credentials. Then run with the `dev` profile:

```bash
export DATABASE_USER=your_db_user
export DATABASE_PASSWORD=your_db_password
export SPRING_PROFILES_ACTIVE=dev
./mvnw org.springframework.boot:spring-boot-maven-plugin:run
```

On Windows PowerShell, set the variables with `$env:DATABASE_USER="your_db_user"`, `$env:DATABASE_PASSWORD="your_db_password"` and `$env:SPRING_PROFILES_ACTIVE="dev"`.

You can also run the `WebApplicationBackendApplication` class from your IDE with the same environment variables.

For the `prod` profile, also provide:

- `DATABASE_URL` (database host / hostname)
- `DATABASE_PORT` (database port)
- `DATABASE_NAME` (database name)

The test suite includes an application-context test that connects to the configured MySQL database. Set the database credentials and ensure MySQL is available before running `./mvnw test`.

## Notes

- This repository is built incrementally: only the `iam` bounded context exposes a REST API for now; delete and update operations are not currently implemented.