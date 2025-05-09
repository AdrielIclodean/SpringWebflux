package com.cognizant.webflux.dto;

import java.time.Instant;
import java.util.UUID;

public record OrderDetailsDTO(
        UUID orderId,
        String customerName,
        String productName,
        Integer amount,
        Instant orderDate
) {
}
