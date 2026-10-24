# Architecture

## Overview

This repository is a small, three-process Spring Boot example. The Maven root
aggregates the applications and manages shared dependency versions; each
application is built and started as its own module.

```text
                         GET /api/items[/...]
Client ── HTTP ──> Gateway (:8080) ─────────────> Items service (:8282)
                       │
                       └── GET /api/orders[/...] ──> Orders service (:8283)
```

The gateway is the public entry point in the example. Both services can also be
called directly on their own ports. No discovery server, database, or
service-to-service call is configured.

## Application responsibilities

| Application | Module | Responsibilities |
| --- | --- | --- |
| Gateway | `gateway/` | Matches request paths to downstream URLs and exposes its health endpoint |
| Items service | `items-service/` | Lists and looks up sample items |
| Orders service | `orders-service/` | Lists and looks up sample orders |

The items and orders applications use Spring MVC (`spring-boot-starter-web`).
The gateway uses Spring Cloud Gateway WebFlux. Keeping these dependencies in
their respective module POMs avoids bringing the gateway stack into the REST
services.

## Request routing

Gateway route definitions are in
[`gateway/src/main/resources/application.yml`](../gateway/src/main/resources/application.yml).
Routes preserve the original request path; there is no path rewrite or prefix
stripping.

| Gateway path | Default downstream URL | Service endpoint |
| --- | --- | --- |
| `/api/items` and `/api/items/**` | `http://localhost:8282` | `items-service` |
| `/api/orders` and `/api/orders/**` | `http://localhost:8283` | `orders-service` |

For example, `GET http://localhost:8080/api/items/1` is forwarded to
`GET http://localhost:8282/api/items/1`. The gateway health endpoint is
`GET http://localhost:8080/actuator/health`.

The downstream base URLs can be changed without editing the route configuration:

| Environment variable | Default |
| --- | --- |
| `ITEMS_SERVICE_URL` | `http://localhost:8282` |
| `ORDERS_SERVICE_URL` | `http://localhost:8283` |

The service ports and application names are set in each service's
`application.yml`.

## REST API behavior

Both APIs currently expose read-only endpoints backed by fixed in-memory sample
lists:

| Service | Method and path | Result |
| --- | --- | --- |
| Items | `GET /api/items` | JSON array of sample items |
| Items | `GET /api/items/{id}` | JSON item or `404 Not Found` |
| Orders | `GET /api/orders` | JSON array of sample orders |
| Orders | `GET /api/orders/{id}` | JSON order or `404 Not Found` |

These are illustrative endpoints, not persistent catalog or order management:
there are no write operations, data validation rules, database repositories,
authentication, or authorization configured.

## Build and run

Run each command from the repository root in a separate terminal:

```shell
mvn -pl items-service spring-boot:run
mvn -pl orders-service spring-boot:run
mvn -pl gateway spring-boot:run
```

To run all module tests:

```shell
mvn test
```

The gateway tests check configured destination URLs and ensure route predicates
do not overlap. The service tests check list, lookup, and not-found behavior
using MockMvc without starting a network server.
