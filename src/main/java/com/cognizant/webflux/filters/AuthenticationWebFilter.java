package com.cognizant.webflux.filters;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@Order(1)
public class AuthenticationWebFilter implements WebFilter {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationWebFilter.class.getName());

    /**
     * This is a dummy map that users need to send the authentication header so that they become Standard or Prime Users
     */
    private static final Map<String, Category> TOKEN_CATEGORY_MAP = Map.of(
            "secret123", Category.STANDARD,
            "secret456", Category.PRIME
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String token = exchange.getRequest().getHeaders().getFirst("Auth");
        if (Objects.nonNull(token) && TOKEN_CATEGORY_MAP.containsKey(token)) {
            exchange.getAttributes().put("TYPE", TOKEN_CATEGORY_MAP.get(token));
            return chain.filter(exchange);
        }

        //return an Empty runnable after the status code was set on the response
        return Mono.fromRunnable(() -> exchange.getResponse().setRawStatusCode(HttpStatus.UNAUTHORIZED.value()));
    }

}
