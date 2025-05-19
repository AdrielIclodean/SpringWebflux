package com.cognizant.webflux.uploadmilionsproducts.service;

import com.cognizant.webflux.uploadmilionsproducts.dto.ProductDTO;
import com.cognizant.webflux.uploadmilionsproducts.mapper.ProductMapper;
import com.cognizant.webflux.uploadmilionsproducts.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service("productServiceMillions")
public class ProductService {

    @Autowired()
    @Qualifier("productRepositoryMillions")
    private ProductRepository productRepository;

    @Autowired
    Sinks.Many<ProductDTO> productSink;

    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono) {
        return productDTOMono.map(ProductMapper::toEntity)
                .flatMap(productRepository::save)
                .map(ProductMapper::toDTO)
                .doOnNext(productSink::tryEmitNext);
    }

    public Flux<ProductDTO> productsAsStream(){
        return this.productSink.asFlux();
    }

     public Flux<ProductDTO> saveProducts(Flux<ProductDTO> productDTOFlux) {
        return productRepository.saveAll(
                        productDTOFlux.map(ProductMapper::toEntity))
                .map(ProductMapper::toDTO);
    }

    public Mono<Long> getProductsCount() {
        return productRepository.count();
    }

    public Flux<ProductDTO> getAllProducts(){
        return productRepository.findAll()
                .map(ProductMapper::toDTO);
    }
}
