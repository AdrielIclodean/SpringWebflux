package com.cognizant.webflux.rest;

import com.cognizant.webflux.dto.CustomerDTO;
import com.cognizant.webflux.exception.ApplicationExceptions;
import com.cognizant.webflux.service.CustomerService;
import com.cognizant.webflux.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers/v2")
public class CustomerRestControllerWithValidation {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Mono<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @GetMapping("/page")
    public Flux<CustomerDTO> getAllCustomersPageable(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return customerService.getCustomersByPage(Pageable.ofSize(size).withPage(page - 1));
    }

    @PostMapping
    public Mono<CustomerDTO> saveCustomer(@RequestBody Mono<CustomerDTO> customerDTO) {
        // if the dto is valid, we will save it
        return customerDTO.transform(RequestValidator.validate())
                .as(customerService::saveCustomer);
    }

    @PutMapping("/{id}")
    public Mono<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody Mono<CustomerDTO> customerDTO) {
        return customerDTO.transform(RequestValidator.validate())
                .as(validReq -> customerService.updateCustomer(id, validReq))
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .then();
    }

}
