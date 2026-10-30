# Architecture Decision Records

This document records significant architecture decisions for OrganiK Platform.
Each record captures the context, decision, and consequences so that future
changes can revisit decisions with their original trade-offs in view.

## Process

- Records are numbered sequentially and use the `ADR-NNNN` identifier.
- New records are appended; accepted records are not silently rewritten.
- When a decision changes, add a new ADR that supersedes the old one and update
  its status to `Superseded` with a link to the replacement.
- Use `Proposed`, `Accepted`, `Rejected`, `Deprecated`, or `Superseded` as status.
- Keep records focused on durable decisions, not implementation notes or
  temporary tasks.

## Index

| ADR                                                              | Title                                      | Status   |
|------------------------------------------------------------------|--------------------------------------------|----------|
| [ADR-0001](#adr-0001-layered-domain-driven-design-structure)     | Layered Domain-Driven Design structure     | Accepted |
| [ADR-0002](#adr-0002-mysql-with-spring-data-jpa-for-persistence) | MySQL with Spring Data JPA for persistence | Accepted |
| [ADR-0003](#adr-0003-rest-api-contract-and-error-responses)      | REST API contract and error responses      | Accepted |

## ADR-0001: Layered Domain-Driven Design structure

- **Status:** Accepted
- **Date:** 2026-10-28

### Context

The service supports the OrganiK web application, which manages inventory,
expiration, supply and conservation for minimarkets of organic products. The
domain has distinct areas such as identity and access (`User`), suppliers,
supply requests, shipment orders and storage conservation. The code also needs
to separate HTTP concerns, use-case orchestration, domain behavior, and
persistence integration.

### Decision

Organize the code into one bounded context per area (`iam`, `inventory`,
`suppliers`, `requisition`, `procurements` and `conservation`), with cross-cutting
components in `shared`. Inside each bounded context, use the `interfaces`,
`application`, `domain`, and `infrastructure` layers. Keep command and query
service contracts in the application layer and their implementations under
`application.internal`. Represent commands and queries as domain-model request
records; application services orchestrate use cases and return domain results.

Each bounded context defines its own value objects, even when another context
uses the same concept (for example, `SupplierId`), so that contexts remain
independent of each other.

### Consequences

- REST controllers depend on application contracts rather than persistence
  implementations.
- Application services coordinate repositories and translate persistence
  outcomes into application results.
- Domain types remain separate from HTTP resources and Spring MVC response
  types.
- The current code has separate command and query services, but shares the
  same persistence model; this does not imply separate read/write databases.
- Bounded contexts can be completed by different people in parallel, because
  each one lives in its own package.
- Some value objects are duplicated across contexts by design.

## ADR-0002: MySQL with Spring Data JPA for persistence

- **Status:** Accepted
- **Date:** 2026-10-28

### Context

Users, suppliers, supply requests, shipment orders and storage readings must
persist across service restarts. Users must be unique by email address. The
application uses Spring Boot and models each concept as a JPA aggregate.

### Decision

Use MySQL for the relational database and Spring Data JPA/Hibernate for
persistence. Persist `User` through `UserRepository`, convert value objects
such as `EmailAddress` and `FullName` to strings with JPA attribute converters,
store enumerations such as `Role` and `UserStatus` as strings, and enforce the
unique email with a named database constraint (`uk_email`). Use the shared
Hibernate physical naming strategy to pluralize table names and convert
identifiers to snake case.

### Consequences

- Database-level uniqueness remains the final guard against concurrent
  duplicate creates; the application also performs an existence pre-check for
  a quicker conflict response.
- Persistence tests and application-context tests require a configured,
  available database unless explicitly isolated from MySQL.
- The domain value objects remain independent of their string storage
  representation.
- Schema changes are applied automatically (`spring.jpa.hibernate.ddl-auto=update`),
  which is convenient for development but does not replace controlled
  migrations in a production deployment.

## ADR-0003: REST API contract and error responses

- **Status:** Accepted
- **Date:** 2026-10-28

### Context

Clients, such as the OrganiK web application, need stable HTTP semantics for
creating and retrieving users, including actionable validation and lookup
errors. HTTP response types should remain outside the domain and application
models.

### Decision

Expose the service under `/api/v1/users` as a REST API. Return `201 Created`
for successful creation, `409 Conflict` for duplicate creation, `200 OK` for
successful retrievals (including an empty list), `404 Not Found` for missing
single-resource lookups, and `400 Bad Request` for invalid input. Represent
errors as Spring `ProblemDetail` responses and resolve user-facing messages
through `MessageSource`. Keep request/response resources and HTTP response
assemblers in the REST interface layer.

### Consequences

- HTTP status and payload translation is localized to the interface layer.
- Error details can be localized using the request locale.
- Changes to these status codes or response shapes are API contract changes
  and should be reflected in API documentation and compatibility review.