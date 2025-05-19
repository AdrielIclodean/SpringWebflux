package com.cognizant.webflux.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;

public class WebclientTest extends AbstractWebClient {

    private final WebClient webClient = createWebClient();

    @Test
    public void testGetProductById() throws InterruptedException {
        webClient.get()
                .uri("/lec01/product/1")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(print())
                .subscribe();

        // need to wait a little for the response
        Thread.sleep(Duration.ofSeconds(2));
    }

    @Test
    public void testConcurrentRequests() throws InterruptedException {
        //every request will run on a different thread

        // all the requests are put in a queue and executed.
        // The thread won't wait for the response, that's why is so fast

        for (int i = 0; i < 100; i++) {
            webClient.get()
                    .uri("/lec01/product/" + i)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnNext(print())
                    .subscribe();
        }

        // need to wait a little for the response
        Thread.sleep(Duration.ofSeconds(2));
    }

}
