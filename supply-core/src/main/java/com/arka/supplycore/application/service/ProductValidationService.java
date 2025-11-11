package com.arka.supplycore.application.service;

import com.arka.supplycore.application.catalog.ProductCatalog;
import com.arka.supplycore.application.dto.SupplyInput;
import java.util.List;
import java.util.Objects;

public record ProductValidationService(ProductCatalog productCatalog) {
  public List<SupplyInput> validateProductInputList(List<SupplyInput> supplyInputList) throws NullPointerException, IllegalArgumentException {
    List<SupplyInput> nonNullList = Objects.requireNonNull(supplyInputList, "details cannot be null");

    if (nonNullList.isEmpty()) {
      throw new IllegalArgumentException("details cannot be empty");
    }

    return nonNullList.stream()
      .filter(input -> productExists(input.getProductId()))
      .toList();
  }

  public boolean productExists(String productId) throws IllegalArgumentException {
    if (productId == null || productId.isBlank()) {
      return false;
    }

    return productCatalog.exists(productId);
  }
}
