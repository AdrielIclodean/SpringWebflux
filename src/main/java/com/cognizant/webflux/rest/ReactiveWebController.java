package com.cognizant.webflux.rest;


import com.cognizant.webflux.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("reactive")
public class ReactiveWebController {
    private static final Logger logger = LoggerFactory.getLogger(ReactiveWebController.class);

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:7070")
            .build();

    //This mapping will not be understood by the browser, so the browser will still wait for the entire response
    @GetMapping("products")
    public Flux<ProductDTO> getProducts() {
        var list = this.webClient.get()
                .uri("/demo01/products")
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                //when an item arrives
                .doOnNext(productDTO -> logger.info("Received product " + productDTO));

        logger.info("Received response " + list);

        return list;
    }

        //This mapping will be understood by the browser, so the browser will not wait for the entire response
        @GetMapping(value = "products/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<ProductDTO> getProductsStream() {
        var list = this.webClient.get()
                .uri("/demo01/products")
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                //when an item arrives
                .doOnNext(productDTO -> logger.info("Received product " + productDTO));

        logger.info("Received response " + list);

        return list;
    }
}
