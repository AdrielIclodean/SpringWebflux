package com.cognizant.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfigSimpleCalculator {

    @Autowired
    private CalculatorHandler calculatorHandler;

    @Bean
    public RouterFunction<ServerResponse> routeForCalculator() {
        String pattern = "/api/calculator/{a}/{b}";
        return RouterFunctions
                .route()
                .filter((req, next) -> {
                    if (req.headers().firstHeader("operation") == null) {
                        return ServerResponse.badRequest().bodyValue("Operation header should be +-/*");
                    }
                    return next.handle(req);
                })
                .filter((req, next) -> {
                    if (!req.pathVariables().containsKey("b") || Integer.parseInt(req.pathVariable("b")) == 0) {
                        return ServerResponse.badRequest().bodyValue("b should be provided and not 0");
                    }
                    return next.handle(req);
                })
                .path(pattern, this::calculatorRoutes)
                .build();
    }

    private RouterFunction<ServerResponse> calculatorRoutes() {
        return RouterFunctions.route()
                .GET(req -> isOperation(req, "+"), calculatorHandler::addition)
                .GET(req -> isOperation(req, "-"), calculatorHandler::subtraction)
                .GET(req -> isOperation(req, "*"), calculatorHandler::multiplication)
                .GET(req -> isOperation(req, "/"), calculatorHandler::division)
                .GET((req) -> ServerResponse.badRequest().bodyValue("Operation is not specified in the header"))
                .build();
    }

    private static boolean isOperation(ServerRequest req, String operation) {
        return operation.equals(req.headers().firstHeader("operation"));
    }
}
