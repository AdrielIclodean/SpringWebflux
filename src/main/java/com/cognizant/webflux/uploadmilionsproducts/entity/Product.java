package com.cognizant.webflux.uploadmilionsproducts.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table(name="productMillions")
public class Product {

    @Id
    private Long id;

    private String description;

    private Integer price;

    public Product() {
    }

    public Product(Long id, String description, Integer price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
