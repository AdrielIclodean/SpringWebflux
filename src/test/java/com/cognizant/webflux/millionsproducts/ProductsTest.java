package com.cognizant.webflux.millionsproducts;

import com.cognizant.webflux.uploadmilionsproducts.dto.ProductDTO;
import com.cognizant.webflux.uploadmilionsproducts.dto.UploadResponse;
import com.cognizant.webflux.uploadmilionsproducts.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.file.Path;
import java.time.Duration;

public class ProductsTest {

    WebClient client = WebClient.create("http://localhost:8080/api/v2/products");

    @Test
    public void testUploadProducts() throws InterruptedException {
        var flux = Flux.range(1, 1_000_000)
                .map(i -> new ProductDTO(null, "Product " + i, i));
        long begin = System.currentTimeMillis();
        uploadProducts(flux).doOnNext(System.out::println)
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - begin) + " ms");
    }

    private Mono<UploadResponse> uploadProducts(Flux<ProductDTO> flux) throws InterruptedException {
        return client.post()
                .uri("/upload")
                .header("Auth", "secret456")
                .contentType(MediaType.APPLICATION_JSON)
                .body(flux, ProductDTO.class)
                .retrieve()
                .bodyToMono(UploadResponse.class);

    }

    @Test
    public void testGetAllProducts() throws InterruptedException {
        Flux<ProductDTO> productDTOFlux = client.get()
                .header("Auth", "secret456")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(ProductDTO.class);

        FileWriter.create(productDTOFlux.map(ProductDTO::toString), Path.of("products.txt"))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }
}
