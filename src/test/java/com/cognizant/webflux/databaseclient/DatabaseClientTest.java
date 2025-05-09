package com.cognizant.webflux.databaseclient;

import com.cognizant.webflux.dto.OrderDetailsDTO;
import com.cognizant.webflux.repository.reactive.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseClientTest extends AbstractTest {

    @Autowired
    DatabaseClient dbClient;

    @Test
    public void testOrderDetailsByProduct() {
        String query = """
                SELECT
                    co.order_id,
                    c.name AS customer_name,
                    p.description AS product_name,
                    co.amount,
                    co.order_date
                FROM
                    customer c
                INNER JOIN customer_order co ON c.id = co.customer_id
                INNER JOIN product p ON p.id = co.product_id
                WHERE
                    p.description = :description
                ORDER BY co.amount DESC
                """;

        dbClient.sql(query)
                .bind("description", "iphone 20")
                .mapProperties(OrderDetailsDTO.class)
                .all()
                .doOnNext(System.out::println)
                .as(StepVerifier::create)
                .assertNext(c -> assertEquals("mike", c.customerName()))
                .assertNext(c -> assertEquals("sam", c.customerName()))
                .expectComplete()
                .verify();
    }
}
