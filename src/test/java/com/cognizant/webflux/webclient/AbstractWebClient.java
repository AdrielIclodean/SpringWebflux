package com.cognizant.webflux.webclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;

public class AbstractWebClient {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebClient.class);

    protected <T> Consumer<T> print(){
        return item -> LOGGER.info("Received item:  {} on thread {}", item, Thread.currentThread().getName());
    }

    protected WebClient createWebClient(Consumer<WebClient.Builder> consumer) {
        WebClient.Builder builder = WebClient.builder()
                .baseUrl("http://localhost:7070/demo02")
                .defaultHeader("Content-Type", "application/json");


        consumer.accept(builder);

        return builder.build();
    }

    protected WebClient createWebClient() {
        return createWebClient(b -> {});
    }

}
