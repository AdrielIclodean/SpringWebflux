package com.cognizant.webflux.config;

import com.cognizant.webflux.dto.CustomerDTO;
import com.cognizant.webflux.exception.ApplicationExceptions;
import com.cognizant.webflux.service.CustomerService;
import com.cognizant.webflux.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerService customerService;

    public Mono<ServerResponse> getAllCustomers(ServerRequest request) {
        return customerService.getAllCustomers()
                .as(flux -> ServerResponse
                        .ok()
                        .body(flux, CustomerDTO.class));
    }


    public Mono<ServerResponse> getAllCustomersPageable(ServerRequest request) {
        int page = request.queryParam("page").map(Integer::parseInt).orElse(0);
        int size = request.queryParam("size").map(Integer::parseInt).orElse(5);
        return customerService.getCustomersByPage(Pageable.ofSize(size).withPage(page))
                .as(flux -> ServerResponse
                        .ok()
                        .body(flux, CustomerDTO.class));

    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request) {
        long id = Long.parseLong(request.pathVariable("id"));
        return customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .as(mono -> ServerResponse.ok().body(mono, CustomerDTO.class));
    }

    public Mono<ServerResponse> createCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDTO.class)
                .transform(RequestValidator.validate())
                .as(customerService::saveCustomer)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest request) {
        long id = Long.parseLong(request.pathVariable("id"));
        return request.bodyToMono(CustomerDTO.class)
                .transform(RequestValidator.validate())
                .as(validatedCustomer -> customerService.updateCustomer(id, validatedCustomer))
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        long id = Long.parseLong(request.pathVariable("id"));
        return customerService.deleteCustomer(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .then(ServerResponse.ok().build());
    }

}
