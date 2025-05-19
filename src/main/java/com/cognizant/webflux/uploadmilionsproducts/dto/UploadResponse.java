package com.cognizant.webflux.uploadmilionsproducts.dto;

import java.util.UUID;

public record UploadResponse(
        UUID confirmationId,
        Long productsCount
) {
}
