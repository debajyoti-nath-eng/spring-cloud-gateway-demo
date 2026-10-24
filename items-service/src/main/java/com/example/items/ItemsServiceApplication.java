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

@SpringBootApplication
@RestController
public class ItemsServiceApplication {

    private final List<Item> items = List.of(
            new Item(1L, "Keyboard", 49.99),
            new Item(2L, "Mouse", 24.99));

    public static void main(String[] args) {
        SpringApplication.run(ItemsServiceApplication.class, args);
    }

    @GetMapping("/api/items")
    public List<Item> getItems() {
        return items;
    }

    @GetMapping("/api/items/{id}")
    public ResponseEntity<Item> getItem(@PathVariable long id) {
        Item item = items.stream()
                .filter(candidate -> candidate.id() == id)
                .findFirst()
                .orElse(null);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    public record Item(long id, String name, double price) {
    }
}
