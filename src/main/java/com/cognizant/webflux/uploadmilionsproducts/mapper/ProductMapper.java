package com.cognizant.webflux.uploadmilionsproducts.mapper;

import com.cognizant.webflux.uploadmilionsproducts.dto.ProductDTO;
import com.cognizant.webflux.uploadmilionsproducts.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductDTO productDTO) {
        return new Product(productDTO.id(), productDTO.description(), productDTO.price());
    }

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getId(), product.getDescription(), product.getPrice());
    }
}
