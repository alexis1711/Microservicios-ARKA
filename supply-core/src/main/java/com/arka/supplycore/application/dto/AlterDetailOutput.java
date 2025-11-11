package com.arka.supplycore.application.dto;

import java.util.List;

public record AlterDetailOutput(String supplyId, List<ProcessedProductDTO> processedProducts, List<FailedProductDto> failedProducts) {
}
