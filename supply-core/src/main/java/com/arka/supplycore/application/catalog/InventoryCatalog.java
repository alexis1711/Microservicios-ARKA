package com.arka.supplycore.application.catalog;

import com.arka.supplycore.application.dto.InventoryDetail;
import java.util.List;

public interface InventoryCatalog {
  /**
   * Sends the signal to update inventory for products
   *
   * @param supplyId ID of the supply
   * @param productDetails List of products to update
   */
  void updateInventory(String supplyId, List<InventoryDetail> productDetails);
}
