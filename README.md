# Spring Cloud Gateway Demo

A reactive API gateway for the Spring Boot REST Demo. Requests to `/api/**`
are forwarded to the items service, including request headers such as
`X-Client-Type`.

## Technology and versions

- Java 21
- Spring Boot 3.5.7
- Spring Cloud 2025.0.3
- Maven 3.6 or newer

## Run

Start the Spring Boot REST Demo on port `8282`, then start the gateway:

```shell
mvn spring-boot:run
```

The gateway listens on port `8080`. The downstream service defaults to
`http://localhost:8282`; set `ITEMS_SERVICE_URL` to change it:

```powershell
$env:ITEMS_SERVICE_URL = "http://localhost:8282"
mvn spring-boot:run
```

## Routes

Base URL: `http://localhost:8080`

| Method | Path | Description |
| --- | --- | --- |
| `*` | `/api/**` | Forward the request to the items service |
| `GET` | `/actuator/health` | Check gateway health |

The gateway passes `X-Client-Type` through to the REST service, which requires
its value to be `UI` or `POSTMAN` for `/api/**` requests. For example:

```shell
curl -i http://localhost:8080/api/items -H "X-Client-Type: POSTMAN"
```

Only the gateway health endpoint is exposed over HTTP; the REST service
continues to handle API validation and item operations.

## Test

```shell
mvn test
```

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for
the full license text. Third-party libraries and tools are subject to their
respective licenses.
