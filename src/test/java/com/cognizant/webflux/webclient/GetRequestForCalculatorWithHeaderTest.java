package com.cognizant.webflux.webclient;

import com.cognizant.webflux.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

public class GetRequestForCalculatorWithHeaderTest extends AbstractWebClient {

    private final WebClient webClient = createWebClient();

    @Test
    public void testGetWithHeader() throws InterruptedException {
        webClient.get()
                .uri("/lec05/calculator/{a}/{b}", 4, 5)
                .header("operation", "asda")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
//                .onErrorReturn(new CalculatorResponse(0, 0, null, null)) // return an empty object if we get an error
                .doOnError(WebClientResponseException.class, ex ->
                        LOGGER.error(ex.getResponseBodyAsString())) // show the ProblemDetail
                .onErrorReturn(WebClientResponseException.BadRequest.class, new CalculatorResponse(0, 0, null, null))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

        // need to wait a little for the response
        Thread.sleep(2000);
    }

    @Test
    public void testGetWithHeaderAndExchange() throws InterruptedException {
        webClient.get()
                .uri("/lec05/calculator/{a}/{b}", 4, 5)
                .header("operation", "adad")
                .exchangeToMono(this::decode)
                .doOnError(WebClientResponseException.class, ex ->
                        LOGGER.error(ex.getResponseBodyAsString())) // show the ProblemDetail
                .onErrorReturn(WebClientResponseException.BadRequest.class, new CalculatorResponse(0, 0, null, null))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

        // need to wait a little for the response
        Thread.sleep(2000);
    }

    private Mono<CalculatorResponse> decode(ClientResponse clientResponse) {
//       clientResponse.headers()
//        clientResponse.cookies()
//        clientResponse.statusCode()
        if (clientResponse.statusCode().is4xxClientError()) {
            return clientResponse.bodyToMono(ProblemDetail.class) // in case we get 4xx we get back a ProblemDetail
                    .doOnNext(r -> LOGGER.info(r.toString()))
                    .then(Mono.empty());
        }
        return clientResponse.bodyToMono(CalculatorResponse.class);

    }

}
