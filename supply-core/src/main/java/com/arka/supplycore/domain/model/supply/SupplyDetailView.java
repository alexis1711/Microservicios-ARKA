package com.arka.supplycore.domain.model.supply;

import java.math.BigDecimal;

public interface SupplyDetailView {
  String getProductId();
  int getQuantity();
  BigDecimal getPrice();
}
