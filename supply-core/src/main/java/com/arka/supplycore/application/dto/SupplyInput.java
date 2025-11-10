package com.arka.supplycore.application.dto;

import com.arka.supplycore.domain.model.supply.SupplyDetailView;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyInput implements SupplyDetailView {
  private String productId;
  private int quantity;
  private BigDecimal price;
}
