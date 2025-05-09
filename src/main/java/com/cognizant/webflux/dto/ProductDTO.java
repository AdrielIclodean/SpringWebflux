package com.cognizant.webflux.dto;

public record ProductDTO(
        Integer id,
        String description,
        Integer price
) {
}
