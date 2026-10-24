/*
 * Copyright (c) 2026 Debajyoti Nath. All rights reserved.
 */
package com.example.orders;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersServiceApplication.class)
class OrdersServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listsOrders() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PROCESSING"));
    }

    @Test
    void getsOrderById() throws Exception {
        mockMvc.perform(get("/api/orders/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PROCESSING"));
    }

    @Test
    void returnsNotFoundForUnknownOrder() throws Exception {
        mockMvc.perform(get("/api/orders/999"))
                .andExpect(status().isNotFound());
    }
}
