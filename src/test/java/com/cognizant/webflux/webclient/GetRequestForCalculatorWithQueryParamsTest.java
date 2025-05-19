package com.cognizant.webflux.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GetRequestForCalculatorWithQueryParamsTest extends AbstractWebClient {

    private final WebClient webClient = createWebClient();

    @Test
    public void testGetWithHeader() throws InterruptedException {

        var path = "lec06/calculator/";
        var query = "first={first}&second={second}&operation={operation}";
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(query).build(10, 20, "+"))
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

}
