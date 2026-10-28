# OrganiK Platform REST API Technical Stories

## Overview
This document contains API-focused technical stories intended for frontend developers integrating with the OrganiK platform REST API.

Base path: `/api/v1`

## Technical Stories

### TS001 — List users by minimarket
**Endpoint:** `GET /api/v1/users?minimarketId={minimarketId}`

As a frontend developer, I want to request the API to list the users of a given `{minimarketId}` so that I can implement the user management feature in my application.
#### Acceptance criteria:
- Scenario: Returns one or more users
    - Given a request to list users filtered by `{minimarketId}` is received
    - When the API finds one or more matching user resources
    - Then the API responds with `200 OK` and returns a non-empty array of user resources, with each resource including `id`, `fullName`, `email`, `role`, `status`, `minimarketId`, and `supplierId`.
- Scenario: Returns no users
    - Given a request to list users filtered by `{minimarketId}` is received
    - When the API finds no matching resources
    - Then the API responds with `200 OK` and returns an empty array.
- Scenario: Invalid parameter value
    - Given a request with a `minimarketId` value that is blank, exceeds 256 characters, or contains disallowed characters is received
    - When the API validates the parameter
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload describing the invalid parameter.
- Scenario: Missing parameters
    - Given a request that provides neither `minimarketId` nor `email` is received
    - When the API validates the parameters
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload describing the missing parameter.
---
### TS002 — Retrieve a user by id
**Endpoint:** `GET /api/v1/users/{id}`

As a frontend developer, I want to request a user by its `{id}` so that I can implement the details view in my application.
#### Acceptance criteria:
- Scenario: Found
    - Given a request for a user identified by `{id}` is received
    - When the API finds the resource
    - Then the API responds with `200 OK` and returns the user resource (`id`, `fullName`, `email`, `role`, `status`, `minimarketId`, `supplierId`).
- Scenario: Not found
    - Given a request for a user identified by a non-existent `{id}` is received
    - When the API does not find the resource
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload describing the missing resource.
---
### TS003 — Retrieve a user by email
**Endpoint:** `GET /api/v1/users?email={email}`

> Note: TS001 and TS003 share the same endpoint. Providing `minimarketId` triggers listing (TS001); providing `email` triggers an exact lookup (TS003). When both are provided, `email` takes precedence.

As a frontend developer, I want to query the API with `{email}` so that I can implement the UI state that checks whether a user already exists.
#### Acceptance criteria:
- Scenario: User exists
    - Given a request that specifies `{email}` is received
    - When the API finds a matching user resource
    - Then the API responds with `200 OK` and returns the matching user resource.
- Scenario: User does not exist
    - Given a request that specifies `{email}` with no match is received
    - When the API does not find a matching resource
    - Then the API responds with `404 Not Found` and a `ProblemDetail` payload describing the missing resource.
- Scenario: Invalid parameter value
    - Given a request with an `email` value that is blank, exceeds 256 characters, or is not a valid email address is received
    - When the API validates the parameter
    - Then the API responds with `400 Bad Request` and a `ProblemDetail` payload describing the invalid parameter.
---
### TS004 — Create a user
**Endpoint:** `POST /api/v1/users`

As a frontend developer, I want to add a user through the API so that I can implement the user creation feature in my application.
#### Acceptance criteria:
- Scenario: Successful create
    - Given a creation request that includes the required fields (`fullName`, `email`, `role`) is received, with `role` being one of `ADMINISTRATOR`, `OPERATOR`, or `SUPPLIER`
    - When the API validates and persists the new resource successfully
    - Then the API responds with `201 Created`, and returns the created resource representation (`id`, `fullName`, `email`, `role`, `status`, `minimarketId`, `supplierId`), where `status` is `PENDING`.
- Scenario: Validation error
    - Given a creation request with missing required fields or containing invalid values is received
    - When the API validation fails
    - Then the API responds with `400 Bad Request`.
- Scenario: Duplicate user
    - Given a creation request for an `email` that already exists is received
    - When the API detects the duplicate constraint violation
    - Then the API responds with `409 Conflict` and a localized `ProblemDetail` payload.