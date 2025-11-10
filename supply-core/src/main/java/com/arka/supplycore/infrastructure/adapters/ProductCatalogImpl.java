package com.arka.supplycore.infrastructure.adapters;

import com.arka.supplycore.application.catalog.ProductCatalog;
import org.springframework.stereotype.Service;

@Service
public class ProductCatalogImpl implements ProductCatalog {
  @Override
  public boolean exists(String productId) {
    // TODO: Connect to svc-products to validate the actual product
    return true;
  }
}
