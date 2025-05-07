package com.cognizant.webflux.model;

public record Product(
        Integer id,
        String description,
        Integer price
) {
}
