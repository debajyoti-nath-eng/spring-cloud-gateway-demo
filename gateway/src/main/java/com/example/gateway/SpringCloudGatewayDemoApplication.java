/*
 * Copyright (c) 2026 Debajyoti Nath. Licensed under the MIT License; see LICENSE.
 */
package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Cloud Gateway application.
 *
 * @author Debajyoti Nath (Technical Architect - Java Backend)
 */
@SpringBootApplication
public class SpringCloudGatewayDemoApplication {

    /**
     * Starts the gateway application.
     *
     * @param args command-line arguments passed to Spring Boot
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayDemoApplication.class, args);
    }
}
