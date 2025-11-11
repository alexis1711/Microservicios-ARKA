package com.arka.supplycore.infrastructure.adapters;

import com.arka.supplycore.application.catalog.InventoryCatalog;
import com.arka.supplycore.application.dto.InventoryDetail;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InventoryCatalogImpl implements InventoryCatalog {
  @Override
  public void updateInventory(String supplyId, List<InventoryDetail> productDetails) {
    // TODO: Connect to svc-inventory to update the inventory
  }
}
