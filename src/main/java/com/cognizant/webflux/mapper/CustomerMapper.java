package com.cognizant.webflux.mapper;

import com.cognizant.webflux.dto.CustomerDTO;
import com.cognizant.webflux.entities.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerDTO customerDTO) {
        return new Customer(customerDTO.id(), customerDTO.name(), customerDTO.email());
    }

    public static CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail());
    }
}
