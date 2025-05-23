package com.cognizant.webflux.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

public class WebclientTest extends AbstractWebClient {

    private final WebClient webClient = createWebClient(b -> {
        var defaultConnectionsPoolSize = 500;
        ConnectionProvider myConnectionProvider = ConnectionProvider.builder("myConnectionProvider")
                .lifo()
                .maxConnections(defaultConnectionsPoolSize)
                .pendingAcquireMaxCount(defaultConnectionsPoolSize * 5)
                .build();

        var httpClient = HttpClient.create(myConnectionProvider)
                .compress(true)
                .keepAlive(true);

        b.clientConnector(new ReactorClientHttpConnector(httpClient));
    });

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
