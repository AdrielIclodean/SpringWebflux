package com.cognizant.webflux.validator;

import com.cognizant.webflux.dto.CustomerDTO;
import com.cognizant.webflux.exception.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<CustomerDTO>> validate() {
        return mono -> mono
                .filter(hasName())
                .switchIfEmpty(Mono.defer(ApplicationExceptions::missingName))
                .filter(hasValidEmail())
                .switchIfEmpty(Mono.defer(ApplicationExceptions::missingValidEmail));
    }

    private static Predicate<CustomerDTO> hasName() {
        return customerDTO -> customerDTO.name() != null && !customerDTO.name().isEmpty();
    }

    private static Predicate<CustomerDTO> hasValidEmail() {
        return customerDTO -> customerDTO.email() != null && customerDTO.email().matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
