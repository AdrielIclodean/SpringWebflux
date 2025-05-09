package com.cognizant.webflux.repository.reactive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;


public class ProductRepositoryTest extends AbstractTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindByPriceBetween() {
        productRepository.findAllByPriceBetween(800, 1000)
                .doOnNext(System.out::println)
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindByPageable() {
        //get the first 3 products
        productRepository.findBy(PageRequest.of(0, 3, Sort.by("price").ascending()))
                .doOnNext(System.out::println)
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals(200, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(250, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(300, p.getPrice()))
                .expectComplete()
                .verify();
    }
}
