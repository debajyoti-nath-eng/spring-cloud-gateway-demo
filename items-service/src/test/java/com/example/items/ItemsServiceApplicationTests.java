/*
 * Copyright (c) 2026 Debajyoti Nath. All rights reserved.
 */
package com.example.items;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemsServiceApplication.class)
class ItemsServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listsItems() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Keyboard"));
    }

    @Test
    void getsItemById() throws Exception {
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }

    @Test
    void returnsNotFoundForUnknownItem() throws Exception {
        mockMvc.perform(get("/api/items/999"))
                .andExpect(status().isNotFound());
    }
}
