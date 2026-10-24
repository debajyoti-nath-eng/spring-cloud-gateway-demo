/*
 * Copyright (c) 2026 Debajyoti Nath. All rights reserved.
 */
package com.example.orders;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Runs the orders REST API and exposes sample order data.
 *
 * <p>The data is intentionally in memory for this demonstration; this service
 * does not call the items service.</p>
 */
@SpringBootApplication
@RestController
public class OrdersServiceApplication {

    private final List<Order> orders = List.of(
            new Order(1001L, "PROCESSING", 74.98),
            new Order(1002L, "SHIPPED", 49.99));

    /**
     * Starts the orders service.
     *
     * @param args command-line arguments passed to Spring Boot
     */
    public static void main(String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }

    /**
     * Lists the sample orders.
     *
     * @return all orders available in this service
     */
    @GetMapping("/api/orders")
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Looks up an order by its numeric identifier.
     *
     * @param id order identifier from the request path
     * @return the order with HTTP 200, or HTTP 404 when it is not present
     */
    @GetMapping("/api/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable long id) {
        Order order = orders.stream()
                .filter(candidate -> candidate.id() == id)
                .findFirst()
                .orElse(null);
        return order == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(order);
    }

    /**
     * Public representation returned by the orders API.
     *
     * @param id order identifier
     * @param status current sample order status
     * @param total sample order total
     */
    public record Order(long id, String status, double total) {
    }
}
