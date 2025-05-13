package com.cognizant.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(CustomerHandler customerHandler) {
        return RouterFunctions
                .route()
                .GET("/api/customers/v3", customerHandler::getAllCustomers)
                .GET("/api/customers/v3/page", customerHandler::getAllCustomersPageable)
                .GET("/api/customers/v3/{id}", customerHandler::getCustomerById)
                .POST("/api/customers/v3", customerHandler::createCustomer)
                .PUT("/api/customers/v3/{id}", customerHandler::updateCustomer)
                .DELETE("/api/customers/v3/{id}", customerHandler::deleteCustomer)
                .filter((request, next) -> ServerResponse.badRequest().build())
                .build();
    }
}
