package com.cognizant.webflux.repository.reactive;

import com.cognizant.webflux.entities.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

    /**
     * We expect to receive multiple customers with the same name, so we return a Flux of customers
     */
    Flux<Customer> findByName(String name);

    Flux<Customer> findBy(Pageable pageable);

    @Modifying
    @Query("delete from customer where id = :id")
    Mono<Boolean> deleteCustomerById(Long id);
}
