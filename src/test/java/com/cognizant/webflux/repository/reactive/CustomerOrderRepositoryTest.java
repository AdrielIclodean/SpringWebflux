package com.cognizant.webflux.repository.reactive;

import com.cognizant.webflux.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerOrderRepositoryTest extends AbstractTest{

     @Autowired
     private CustomerOrderRepository customerOrderRepository;

     @Test
     void testGetProductsOrderedByCustomerId() {
         Long customerId = 1L;
         customerOrderRepository.getProductsOrderedByCustomerId(customerId)
                 .doOnNext(System.out::println)
                 .as(StepVerifier::create)
                 .assertNext(c -> assertEquals("iphone 18", c.getDescription()))
                 .assertNext(c -> assertEquals("iphone 20", c.getDescription()))
                 .expectComplete()
                 .verify();
     }


     @Test
     void testGetOrderDetails() {
         Long customerId = 1L;
         customerOrderRepository.getOrderDetailsByProduct("iphone 20")
                 .doOnNext(System.out::println)
                 .as(StepVerifier::create)
                 .assertNext(c -> assertEquals("mike", c.customerName()))
                 .assertNext(c -> assertEquals("sam", c.customerName()))
                 .expectComplete()
                 .verify();
     }


}
