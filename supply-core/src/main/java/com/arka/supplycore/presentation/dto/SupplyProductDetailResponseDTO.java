package com.arka.supplycore.presentation.dto;

import com.arka.supplycore.application.dto.ProcessedProductDTO;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SupplyProductDetailResponseDTO {
  private String productId;
  private int quantity;
  private BigDecimal price;

  public SupplyProductDetailResponseDTO(ProcessedProductDTO processedProductDTO) {
    this.productId = processedProductDTO.productId();
    this.quantity = processedProductDTO.quantity();
    this.price = processedProductDTO.price();
  }
}
