package com.cognizant.webflux.repository.reactive;

import com.cognizant.webflux.dto.OrderDetailsDTO;
import com.cognizant.webflux.entities.CustomerOrder;
import com.cognizant.webflux.entities.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder, Long> {

    @Query("""
            SELECT p.*
            FROM customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON co.product_id = p.id
            WHERE c.id = :customerId
            Order By p.price
            """
    )
    Flux<Product> getProductsOrderedByCustomerId(Long customerId);

    @Query("""
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
            """)
    Flux<OrderDetailsDTO> getOrderDetailsByProduct(String description);
}
