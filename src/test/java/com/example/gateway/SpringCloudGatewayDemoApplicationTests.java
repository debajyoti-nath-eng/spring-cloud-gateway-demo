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
 * Tests the gateway application and its configured items-service route.
 *
 * @author Debajyoti Nath (Technical Architect - Java Backend)
 */
@SpringBootTest
@TestPropertySource(properties = "ITEMS_SERVICE_URL=http://items.example:8282")
class SpringCloudGatewayDemoApplicationTests {

    private static final Duration ROUTE_LOOKUP_TIMEOUT = Duration.ofSeconds(5);

    @Autowired
    private RouteLocator routeLocator;

    /**
     * Verifies the items route forwards to the configured downstream service.
     */
    @Test
    void itemsRouteUsesConfiguredServiceUri() {
        Route route = routeLocator.getRoutes()
                .filter(candidate -> candidate.getId().equals("items-service"))
                .blockFirst(ROUTE_LOOKUP_TIMEOUT);

        assertThat(route).isNotNull();
        assertThat(route.getUri().toString()).isEqualTo("http://items.example:8282");
        assertThat(Mono.from(route.getPredicate().apply(exchangeFor("/api/items")))
                .block(ROUTE_LOOKUP_TIMEOUT)).isTrue();
        assertThat(Mono.from(route.getPredicate().apply(exchangeFor("/actuator/health")))
                .block(ROUTE_LOOKUP_TIMEOUT)).isFalse();
    }

    private MockServerWebExchange exchangeFor(String path) {
        return MockServerWebExchange.from(MockServerHttpRequest.get(path).build());
    }
}
