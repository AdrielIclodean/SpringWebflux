package com.cognizant.webflux.filters;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static org.springframework.http.HttpMethod.GET;

@Service
@Order(2)
public class AuthorizationWebFilter implements WebFilter {

    private static final Logger LOGGER = Logger.getLogger(AuthorizationWebFilter.class.getName());


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        if (exchange.getAttribute("TYPE") == Category.PRIME) {
            LOGGER.info("Prime User");
            return prime(exchange, chain);
        }

        LOGGER.info("Standard User");
        return standard(exchange, chain);
    }


    public Mono<Void> standard(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getMethod() == GET) {
            return chain.filter(exchange);
        }
        return Mono.fromRunnable(() -> exchange.getResponse().setRawStatusCode(HttpStatus.FORBIDDEN.value()));
    }


    public Mono<Void> prime(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange);
    }

}
