package com.cognizant.webflux.webclient;

import com.cognizant.webflux.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.UUID;

public class ExchangeFilterTest extends AbstractWebClient {

    private final WebClient webClient = createWebClient(
            b -> b.filter(tokenGenerator()).filter(loggingRequest()));


    @Test
    public void testGetProductById() throws InterruptedException {
        webClient.get()
                .uri("/lec09/product/{id}", 1)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

        // need to wait a little for the response
        Thread.sleep(Duration.ofSeconds(2));
    }

    private ExchangeFilterFunction tokenGenerator() {
        return ((request, next) -> {
            var token = UUID.randomUUID().toString().replace("-", "");
            LOGGER.info("We created the token for authentication {}", token);

            // the request is immutable
            // we cannot just do request.headers().setBearerAuth(token);

            return next.exchange(ClientRequest.from(request).headers(h -> h.setBearerAuth(token))
                    .build());
        });
    }

    private ExchangeFilterFunction loggingRequest(){
        return ((request, next) -> {

            LOGGER.info("Logging the request URI filter: {}", request.url());

            return next.exchange(request);
        });
    }

}
