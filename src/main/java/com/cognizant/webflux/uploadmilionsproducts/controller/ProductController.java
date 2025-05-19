package com.cognizant.webflux.uploadmilionsproducts.controller;

import com.cognizant.webflux.uploadmilionsproducts.dto.ProductDTO;
import com.cognizant.webflux.uploadmilionsproducts.dto.UploadResponse;
import com.cognizant.webflux.uploadmilionsproducts.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v2/products")
public class ProductController {

    private static final Logger log = Logger.getLogger(ProductController.class.getName());
    private final ProductService productService;

    ProductController(@Qualifier("productServiceMillions") ProductService productService){
        this.productService = productService;
    }

    @PostMapping(value = "upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UploadResponse> upload(@RequestBody Flux<ProductDTO> flux){
        return this.productService.saveProducts(
                flux.doOnNext(p -> log.info("Saving product: " + p)))
                .then(this.productService.getProductsCount())
                .map(count -> new UploadResponse(UUID.randomUUID(), count));
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UploadResponse> saveProduct(@RequestBody Mono<ProductDTO> mono){
        return this.productService.saveProduct(
                mono.doOnNext(p -> log.info("Saving product: " + p)))
                .then(this.productService.getProductsCount())
                .map(count -> new UploadResponse(UUID.randomUUID(), count));
    }

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductDTO> getAllProducts() {
        return this.productService.getAllProducts();
    }


    @GetMapping(path = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDTO> getProductsStream() {
        return this.productService.productsAsStream();
    }
}
