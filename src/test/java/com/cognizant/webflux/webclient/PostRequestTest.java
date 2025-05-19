package com.cognizant.webflux.webclient;

import com.cognizant.webflux.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

public class PostRequestTest extends AbstractWebClient{

    private final WebClient webClient = createWebClient();

    @Test
    public void testPostBody() throws InterruptedException {
        var product = new ProductDTO(null, "THis is my special product", 100);
        webClient.post()
                .uri("/lec03/product")
                .bodyValue(product)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .doOnNext(print())
                .subscribe();

        // need to wait a little for the response
        Thread.sleep(2000);
    }
}
