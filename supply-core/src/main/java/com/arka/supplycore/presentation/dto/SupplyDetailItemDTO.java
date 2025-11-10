package com.arka.supplycore.presentation.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyDetailItemDTO {
  private String productId;
  private int quantity;
  private BigDecimal price;
}
