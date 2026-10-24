# Spring Cloud Gateway Demo

A Spring Cloud Gateway application with independently runnable items and orders
REST services. The gateway forwards each API path to the matching service.
See [the architecture guide](docs/architecture.md) for component responsibilities,
request flow, configuration, and current demo limitations.

## Technology and versions

- Java 21
- Spring Boot 3.5.7
- Spring Cloud 2025.0.3
- Maven 3.6 or newer

## Run

From the repository root, start each application in a separate terminal:

```shell
mvn -pl items-service spring-boot:run
mvn -pl orders-service spring-boot:run
mvn -pl gateway spring-boot:run
```

The gateway listens on port `8080`, the items service on `8282`, and the orders
service on `8283`. Change downstream service URLs with `ITEMS_SERVICE_URL` and
`ORDERS_SERVICE_URL`:

```powershell
$env:ITEMS_SERVICE_URL = "http://localhost:8282"
$env:ORDERS_SERVICE_URL = "http://localhost:8283"
mvn -pl gateway spring-boot:run
```

## Routes

Base URL: `http://localhost:8080`

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/api/items` | List items |
| `GET` | `/api/items/{id}` | Get an item by ID |
| `GET` | `/api/orders` | List orders |
| `GET` | `/api/orders/{id}` | Get an order by ID |
| `GET` | `/actuator/health` | Check gateway health |

The gateway forwards `/api/items/**` to the items service and `/api/orders/**`
to the orders service. The services return sample in-memory data. For example:

```shell
curl http://localhost:8080/api/items
curl http://localhost:8080/api/orders/1001
```

Unknown item and order IDs return `404 Not Found`.

## Project layout

| Module | Responsibility |
| --- | --- |
| `gateway/` | Reactive gateway routes, port `8080`, and health endpoint |
| `items-service/` | Sample items REST API, port `8282` |
| `orders-service/` | Sample orders REST API, port `8283` |

Each module declares its own application dependencies. The root Maven project
manages shared versions and aggregates the modules.

## Test

```shell
mvn test
```

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for
the full license text. Third-party libraries and tools are subject to their
respective licenses.
