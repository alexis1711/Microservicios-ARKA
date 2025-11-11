package com.arka.supplycore.application.dto;

import java.math.BigDecimal;

public record ProcessedProductDTO(String productId, int quantity, BigDecimal price) {
}
