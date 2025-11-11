package com.arka.supplycore.domain.model.supply;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This model it's intended to support all the details from
 * purchases made to providers as a domain entity
 */
class SupplyDetail implements SupplyDetailView {
  private final String productId;
  private final int quantity;
  private final BigDecimal price;

  SupplyDetail(String productId, int quantity, BigDecimal price) {
    this.productId = productId.toLowerCase();

    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }
    this.quantity = quantity;

    if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Unit Price cannot be null or negative");
    }
    this.price = price.setScale(2, RoundingMode.HALF_UP);
  }

  public BigDecimal subtotal() {
    return price.multiply(BigDecimal.valueOf(quantity));
  }

  @Override
  public String getProductId() {
    return productId;
  }

  @Override
  public int getQuantity() {
    return quantity;
  }

  @Override
  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }

    if (other instanceof SupplyDetail casted) {
      return this.productId.equals(casted.productId);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return this.productId.hashCode();
  }
}
