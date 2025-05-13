package com.cognizant.webflux.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CalculatorHandler {
    public Mono<ServerResponse> addition(ServerRequest request) {
        int a = Integer.parseInt(request.pathVariable("a"));
        int b = Integer.parseInt(request.pathVariable("b"));

        return ServerResponse.ok().bodyValue(a + b);
    }

    public Mono<ServerResponse> subtraction(ServerRequest request) {
        int a = Integer.parseInt(request.pathVariable("a"));
        int b = Integer.parseInt(request.pathVariable("b"));

        return ServerResponse.ok().bodyValue(a - b);
    }

    public Mono<ServerResponse> multiplication(ServerRequest request) {
        int a = Integer.parseInt(request.pathVariable("a"));
        int b = Integer.parseInt(request.pathVariable("b"));

        return ServerResponse.ok().bodyValue(a * b);

    }

    public Mono<ServerResponse> division(ServerRequest request) {
        int a = Integer.parseInt(request.pathVariable("a"));
        int b = Integer.parseInt(request.pathVariable("b"));

        return ServerResponse.ok().bodyValue(a / b);
    }
}
