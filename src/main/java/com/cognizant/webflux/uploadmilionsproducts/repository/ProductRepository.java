package com.cognizant.webflux.uploadmilionsproducts.repository;

import com.cognizant.webflux.uploadmilionsproducts.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository("productRepositoryMillions")
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    Flux<Product> findAllByPriceBetween(Integer min, Integer max);

    Flux<Product> findBy(Pageable pageable);
}
