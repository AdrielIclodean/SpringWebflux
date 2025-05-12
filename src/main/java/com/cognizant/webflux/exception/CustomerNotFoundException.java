package com.cognizant.webflux.exception;

public class CustomerNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Customer not found with id: %s";

    public CustomerNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
