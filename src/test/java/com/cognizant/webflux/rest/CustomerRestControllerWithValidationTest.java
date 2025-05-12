package com.cognizant.webflux.rest;

import com.cognizant.webflux.dto.CustomerDTO;
import com.cognizant.webflux.repository.reactive.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

// used for testing REST APIs
@AutoConfigureWebTestClient
public class CustomerRestControllerWithValidationTest extends AbstractTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void testGetAllCustomers() {
        client.get()
                .uri("/api/customers/v2")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerDTO.class)
                .hasSize(10);
    }

    @Test
    public void testGetAllCustomersPageable() {
        client.get()
                .uri("/api/customers/v2/page?page=1&size=3")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(
                        p -> System.out.println(new String(p.getResponseBody())) // print the response body
                )
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[0].name").isEqualTo("sam")
                .jsonPath("$[1].name").isEqualTo("mike");
    }

    @Test
    public void testCustomerById() {
        client.get()
                .uri("/api/customers/v2/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(
                        p -> System.out.println(new String(p.getResponseBody()))
                )
                .jsonPath("$.name").isEqualTo("sam");
    }

    @Test
    public void testCreateAndDeleteCustomer() {
        var customer = new CustomerDTO(null, "test", "test@email.com");
        client.post()
                .uri("/api/customers/v2")
                .bodyValue(customer)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(
                        p -> System.out.println(new String(p.getResponseBody()))
                )
                .jsonPath("$.name").isEqualTo("test");

        client.delete()
                .uri("/api/customers/v2/11")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testCustomerNotFound() {
        client.get()
                .uri("/api/customers/v2/100")
                .exchange()
//                .expectStatus().isNotFound();
//                or
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.status").isEqualTo("404")
                .jsonPath("$.detail").isEqualTo("Customer not found with id: 100");

        client.delete()
                .uri("/api/customers/v2/100")
                .exchange()
                .expectBody()
                .isEmpty();

        var customer = new CustomerDTO(100L, "test", "someEmail");
        client.put()
                .uri("/api/customers/v2/100")
                .bodyValue(customer)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.status").isEqualTo("404")
                .jsonPath("$.detail").isEqualTo("Customer not found with id: 100");
    }

    @Test
    public void testInvalidInput(){
        var customer = new CustomerDTO(null, "", "test@email.com");
        client.post()
                .uri("/api/customers/v2")
                .bodyValue(customer)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(
                        p -> System.out.println(new String(p.getResponseBody()))
                )
                .jsonPath("$.title").isEqualTo("Invalid input")
                .jsonPath("$.detail").isEqualTo("Name is required");

    }
}
