package com.cognizant.webflux.repository.reactive;

import com.cognizant.webflux.entities.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

    /**
     * We expect to receive multiple customers with the same name, so we return a Flux of customers
     */
    Flux<Customer> findByName(String name);
}
