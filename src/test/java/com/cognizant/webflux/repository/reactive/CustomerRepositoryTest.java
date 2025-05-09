package com.cognizant.webflux.repository.reactive;

import com.cognizant.webflux.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerRepositoryTest extends AbstractTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindAll() {
        customerRepository.findAll()
                .doOnNext(System.out::println)
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindById() {
        customerRepository.findById(1L)
                .doOnNext(System.out::println)
                .as(StepVerifier::create)
                .assertNext(c -> assertEquals("sam", c.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindByName() {
        customerRepository.findByName("sam")
                .doOnNext(System.out::println)
                .as(StepVerifier::create)
                .assertNext(c -> assertEquals("sam@gmail.com", c.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void testInsertAndDelete() {
        var cus = new Customer();
        cus.setName("test");
        cus.setEmail("test@email.com");
        customerRepository.save(cus)
                .doOnNext(System.out::println)
                .as(StepVerifier::create)
                .assertNext(c -> assertNotNull(c.getId()))
                .expectComplete()
                .verify();

        this.customerRepository.count()
                .as(StepVerifier::create)
                .expectNext(11L)
                .expectComplete()
                .verify();

        //delete it and expect the count to be 10
        this.customerRepository.deleteById(11l)
                .then(this.customerRepository.count())
                .as(StepVerifier::create)
                .expectNext(10L)
                .expectComplete()
                .verify();
    }

    @Test
    public void testUpdateCustomer(){
        customerRepository.findByName("ethan")
                .doOnNext(c -> c.setName("ethan_updated"))
                .flatMap(c -> customerRepository.save(c))
                .doOnNext(System.out::println)
                .as(StepVerifier::create)
                .assertNext(c -> assertEquals("ethan_updated", c.getName()))
                .expectComplete()
                .verify();
    }


}
