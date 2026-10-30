# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

First documented state of the OrganiK Platform: a Spring Boot REST service for
the OrganiK web application, organized into the `iam`, `suppliers`,
`requisition`, `procurements`, `conservation` and `shared` bounded contexts
following Domain-Driven Design.

### Added

- Users REST API under `/api/v1/users`:
  - `POST /api/v1/users` to create a user.
  - `GET /api/v1/users/{id}` to retrieve a user by its identifier.
  - `GET /api/v1/users` to list users by minimarket ID, or retrieve one by email address.
- `iam` bounded context:
  - `User` aggregate, `FullName`, `EmailAddress`, `MinimarketId` and `SupplierId` value objects, `Role` and `UserStatus` enumerations, and the `CreateUserCommand` command.
  - Queries to get users by id, by minimarket ID, and by email address.
  - Command and query services with their application-layer contracts and `UserCommandFailure`.
  - Spring Data JPA `UserRepository` and JPA attribute converters for the value objects.
  - REST resources and assemblers that map commands, queries, and their results to HTTP responses.
- Inventory REST API:
  - `POST /api/v1/stock-lots`, `GET /api/v1/stock-lots/{id}` and `GET /api/v1/stock-lots?minimarketId=...`.
  - `POST /api/v1/inventory-movements`, `GET /api/v1/inventory-movements/{id}` and `GET /api/v1/inventory-movements?stockLotId=...`.
  - `POST /api/v1/offers`, `GET /api/v1/offers/{id}` and `GET /api/v1/offers?stockLotId=...`.
- `inventory` bounded context:
  - `StockLot`, `InventoryMovement` and `Offer` aggregates, their value objects, commands and JPA converters.
  - Queries, Spring Data JPA repositories, and command and query services with `StockLotCommandFailure`, `InventoryMovementCommandFailure` and `OfferCommandFailure`.
  - REST resources and assemblers that map commands, queries, and their results to HTTP responses.
- `suppliers` bounded context: `Supplier` and `OfferedProduct` aggregates, their value objects, commands and JPA converters.
- `requisition` bounded context: `SupplyRequest` aggregate, its value objects, command and JPA converters.
- `procurements` bounded context: `ShipmentOrder` aggregate with its `ShipmentOrderLine` entity, value objects, commands to create, accept and reject an order, and JPA converters.
- `conservation` bounded context: `StorageZone` and `StorageReading` aggregates, their value objects, commands and JPA converters.
- `shared` bounded context:
  - `Result` abstraction for command outcomes.
  - `SnakeCasePhysicalNamingStrategy`, converting identifiers to snake_case and using plural table names.
- Environment-specific configuration for the default, `dev`, and `prod` Spring profiles, with database credentials resolved from environment variables.
- Internationalized validation and error messages (`messages.properties`, `messages_es.properties`).
- JPA auditing is enabled for the application.
- SpringDoc OpenAPI / Swagger UI for API documentation.
- Project documentation: `README.md`, `CONTRIBUTING.md`, `LICENSE.md` (MIT), architecture decision records (`docs/adrs.md`), a class diagram (`docs/class-diagram.puml`), and technical user stories (`docs/user-stories.md`).
- Project metadata in `pom.xml`: MIT license and developer information.

### Changed

- Dependency and build updates in `pom.xml`, including Lombok, SpringDoc, MySQL connector, Jackson, Logback, Tomcat, and `maven-compiler-plugin`.