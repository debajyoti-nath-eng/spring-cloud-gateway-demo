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

@SpringBootApplication
@RestController
public class OrdersServiceApplication {

    private final List<Order> orders = List.of(
            new Order(1001L, "PROCESSING", 74.98),
            new Order(1002L, "SHIPPED", 49.99));

    public static void main(String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }

    @GetMapping("/api/orders")
    public List<Order> getOrders() {
        return orders;
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable long id) {
        Order order = orders.stream()
                .filter(candidate -> candidate.id() == id)
                .findFirst()
                .orElse(null);
        return order == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(order);
    }

    public record Order(long id, String status, double total) {
    }
}
