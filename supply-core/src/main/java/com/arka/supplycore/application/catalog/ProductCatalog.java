package com.arka.supplycore.application.catalog;

import com.arka.supplycore.application.exception.BusinessException;

public interface ProductCatalog {
  boolean exists(String productId) throws BusinessException;
}
