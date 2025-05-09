package com.cognizant.webflux.repository.reactive;

import com.cognizant.webflux.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    Flux<Product> findAllByPriceBetween(Integer min, Integer max);

    Flux<Product> findBy(Pageable pageable);
}
