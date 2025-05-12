package com.cognizant.webflux.service;

import com.cognizant.webflux.dto.CustomerDTO;
import com.cognizant.webflux.mapper.CustomerMapper;
import com.cognizant.webflux.repository.reactive.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Flux<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .map(CustomerMapper::toDTO);
    }

    public Mono<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(CustomerMapper::toDTO);
    }

    public Mono<CustomerDTO> saveCustomer(Mono<CustomerDTO> customerDTO) {
        //some validation logic should be implemented
        return customerDTO.map(CustomerMapper::toEntity)
                .flatMap(customerRepository::save)
                .map(CustomerMapper::toDTO);
    }

    public Mono<CustomerDTO> updateCustomer(Long id, Mono<CustomerDTO> customerDTO) {
        return customerRepository.findById(id)
                .flatMap(c -> customerDTO) //subscribe to the Mono
                .map(CustomerMapper::toEntity) // convert to entity
                .doOnNext(c -> c.setId(id)) // set the id
                .flatMap(customer -> customerRepository.save(customer)) // save the entity
                .map(CustomerMapper::toDTO); // convert to DTO
    }

    public Mono<Boolean> deleteCustomer(Long id) {
        return customerRepository.deleteCustomerById(id);
    }

    public Flux<CustomerDTO> getCustomersByPage(Pageable pageable){
        return customerRepository.findBy(pageable)
                .map(CustomerMapper::toDTO);
    }
}
