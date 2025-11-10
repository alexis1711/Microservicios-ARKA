package com.arka.supplycore.application.dto;

import java.util.List;

public record AddDetailOutput(String supplyId, List<ProcessedProductDTO> processedProducts, List<FailedProductDto> failedProducts) {
}
