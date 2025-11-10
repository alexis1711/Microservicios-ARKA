package com.arka.supplycore.application.dto;

import java.math.BigDecimal;

public record FailedProductDto(String productId, int quantity, BigDecimal price, String reason) {
}
