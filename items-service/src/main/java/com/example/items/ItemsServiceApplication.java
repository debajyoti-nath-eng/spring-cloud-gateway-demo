/*
 * Copyright (c) 2026 Debajyoti Nath. All rights reserved.
 */
package com.example.items;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Runs the items REST API and exposes sample catalog data.
 *
 * <p>The data is intentionally in memory for this demonstration and is not
 * shared with the orders service.</p>
 */
@SpringBootApplication
@RestController
public class ItemsServiceApplication {

    private final List<Item> items = List.of(
            new Item(1L, "Keyboard", 49.99),
            new Item(2L, "Mouse", 24.99));

    /**
     * Starts the items service.
     *
     * @param args command-line arguments passed to Spring Boot
     */
    public static void main(String[] args) {
        SpringApplication.run(ItemsServiceApplication.class, args);
    }

    /**
     * Lists the sample items.
     *
     * @return all items available in this service
     */
    @GetMapping("/api/items")
    public List<Item> getItems() {
        return items;
    }

    /**
     * Looks up an item by its numeric identifier.
     *
     * @param id item identifier from the request path
     * @return the item with HTTP 200, or HTTP 404 when it is not present
     */
    @GetMapping("/api/items/{id}")
    public ResponseEntity<Item> getItem(@PathVariable long id) {
        Item item = items.stream()
                .filter(candidate -> candidate.id() == id)
                .findFirst()
                .orElse(null);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    /**
     * Public representation returned by the items API.
     *
     * @param id item identifier
     * @param name display name
     * @param price sample price
     */
    public record Item(long id, String name, double price) {
    }
}
