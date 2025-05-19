package com.cognizant.webflux.webclient;

public record CalculatorResponse(
        Integer first,
        Integer second,
        String operation,
        Integer result
) {
}
