package com.cognizant.webflux.uploadmilionsproducts.dto;

public record ProductDTO(
        Long id,
        String description,
        Integer price
) {
}
