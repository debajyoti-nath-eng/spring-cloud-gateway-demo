/*
 * Copyright (c) 2026 Debajyoti Nath. Licensed under the MIT License; see LICENSE.
 */
package com.example.gateway;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.context.TestPropertySource;

import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the gateway application and its configured downstream-service routes.
 *
 * @author Debajyoti Nath (Technical Architect - Java Backend)
 */
@SpringBootTest
@TestPropertySource(properties = {
        "ITEMS_SERVICE_URL=http://items.example:8282",
        "ORDERS_SERVICE_URL=http://orders.example:8283"
})
class SpringCloudGatewayDemoApplicationTests {

    private static final Duration ROUTE_LOOKUP_TIMEOUT = Duration.ofSeconds(5);

    @Autowired
    private RouteLocator routeLocator;

    @Test
    void itemsRouteUsesConfiguredServiceUri() {
        Route route = routeById("items-service");

        assertThat(route.getUri().toString()).isEqualTo("http://items.example:8282");
        assertThat(matches(route, "/api/items")).isTrue();
        assertThat(matches(route, "/api/orders")).isFalse();
    }

    @Test
    void ordersRouteUsesConfiguredServiceUri() {
        Route route = routeById("orders-service");

        assertThat(route.getUri().toString()).isEqualTo("http://orders.example:8283");
        assertThat(matches(route, "/api/orders")).isTrue();
        assertThat(matches(route, "/api/items")).isFalse();
    }

    private Route routeById(String id) {
        Route route = routeLocator.getRoutes()
                .filter(candidate -> candidate.getId().equals(id))
                .blockFirst(ROUTE_LOOKUP_TIMEOUT);
        assertThat(route).isNotNull();
        return route;
    }

    private boolean matches(Route route, String path) {
        return Boolean.TRUE.equals(Mono.from(route.getPredicate().apply(exchangeFor(path)))
                .block(ROUTE_LOOKUP_TIMEOUT));
    }

    private MockServerWebExchange exchangeFor(String path) {
        return MockServerWebExchange.from(MockServerHttpRequest.get(path).build());
    }
}
