package com.cognizant.webflux.webclient;


import com.cognizant.webflux.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxStreamTest extends AbstractWebClient {

    private final WebClient webClient = createWebClient();

    @Test
    public void testGetProductByIdStreaming() throws InterruptedException {
        webClient.get()
                .uri("/lec02/product/stream")
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                .take(Duration.ofSeconds(3)) //get products for 3 seconds
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

        // need to wait a little for the response
        Thread.sleep(Duration.ofSeconds(2));
    }


}
