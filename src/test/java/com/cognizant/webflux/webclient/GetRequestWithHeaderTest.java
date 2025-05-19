package com.cognizant.webflux.webclient;

import com.cognizant.webflux.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

public class GetRequestWithHeaderTest extends AbstractWebClient{

    private final WebClient webClient = createWebClient();

    @Test
    public void testGetWithHeader() throws InterruptedException {
        var product = new ProductDTO(null, "THis is my special product", 100);
        webClient.get()
                .uri("/lec04/product/{id}", 1)
                .header("caller-id", "order-service")
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

        // need to wait a little for the response
        Thread.sleep(2000);
    }

    @Test
    public void testGetWithMapHeader() throws InterruptedException {
        var product = new ProductDTO(null, "THis is my special product", 100);

        var headers = Map.of(
                "caller-id", "new-value",
                "another-header", "another-value"
        );

        webClient.get()
                .uri("/lec04/product/{id}", 1)
                .headers(h -> h.setAll(headers))
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

        // need to wait a little for the response
        Thread.sleep(2000);
    }
}
