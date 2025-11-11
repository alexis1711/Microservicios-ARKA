package com.arka.supplycore.infrastructure.persistence.entity;

import com.arka.supplycore.domain.model.supply.SupplyDetailView;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyDetailEntity implements SupplyDetailView {
  private String productId;
  private int quantity;
  private BigDecimal price;

  public SupplyDetailEntity(SupplyDetailView supplyDetailView) {
    this.productId = supplyDetailView.getProductId();
    this.quantity = supplyDetailView.getQuantity();
    this.price = supplyDetailView.getPrice();
  }
}
