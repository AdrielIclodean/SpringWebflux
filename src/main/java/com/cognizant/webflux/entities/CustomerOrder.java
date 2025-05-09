package com.cognizant.webflux.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table
public class CustomerOrder {

    @Id
    private UUID id;
    private Integer customerId;
    private Integer productId;
    private Integer amount;
    private Instant orderDate;

    public CustomerOrder() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", amount=" + amount +
                ", orderDate=" + orderDate +
                '}';
    }
}
