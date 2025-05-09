package com.cognizant.webflux.rest;

import com.cognizant.webflux.dto.CustomerDTO;
import com.cognizant.webflux.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerDTO>> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<CustomerDTO> saveCustomer(@RequestBody Mono<CustomerDTO> customerDTO) {
        return customerService.saveCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerDTO>> updateCustomer(@PathVariable Long id, @RequestBody Mono<CustomerDTO> customerDTO) {
        return customerService.updateCustomer(id, customerDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }
}
