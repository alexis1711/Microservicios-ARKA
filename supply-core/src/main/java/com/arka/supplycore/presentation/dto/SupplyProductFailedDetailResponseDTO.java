package com.arka.supplycore.presentation.dto;

import com.arka.supplycore.application.dto.FailedProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SupplyProductFailedDetailResponseDTO extends SupplyProductDetailResponseDTO {
  private String reason;

  public SupplyProductFailedDetailResponseDTO(FailedProductDto failedProductDto) {
    super.setProductId(failedProductDto.productId());
    super.setPrice(failedProductDto.price());
    super.setQuantity(failedProductDto.quantity());
    reason = failedProductDto.reason();
  }
}
