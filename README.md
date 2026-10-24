# Spring Cloud Gateway Demo

A Spring Cloud Gateway application with independently runnable items and orders
REST services. The gateway forwards each API path to the matching service.

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

## Test

```shell
mvn test
```

## License

All rights reserved. This repository may be viewed and cloned, but no license
is granted to use, reproduce, modify, distribute, or create derivative works
from its contents, except as permitted by applicable law or the GitHub Terms
of Service. See [LICENSE](LICENSE).
