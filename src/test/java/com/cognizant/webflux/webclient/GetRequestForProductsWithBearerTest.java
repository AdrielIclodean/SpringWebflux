package com.cognizant.webflux.webclient;

import com.cognizant.webflux.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

public class GetRequestForProductsWithBearerTest extends AbstractWebClient {

    private final WebClient webClient = createWebClient(
            b -> b.defaultHeaders(
                    h -> h.setBearerAuth("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")));

    @Test
    public void testGetWithHeader() throws InterruptedException {
        var path = "/lec08/product/{id}";
        webClient.get()
                .uri(path, 1)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .doOnError(WebClientResponseException.class,
                        ex -> LOGGER.error(ex.getResponseBodyAsString()))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

        // need to wait a little for the response
        Thread.sleep(2000);
    }

}
