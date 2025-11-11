package com.arka.supplycore.application.usecase;

import com.arka.supplycore.application.catalog.InventoryCatalog;
import com.arka.supplycore.application.dto.InventoryDetail;
import com.arka.supplycore.application.exception.DataNotFoundException;
import com.arka.supplycore.application.service.SupplyOrderValidationService;
import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.model.supply.SupplyDetailView;
import com.arka.supplycore.domain.repository.SupplyRepository;
import java.util.List;

public record ProcessSupply(SupplyRepository supplyRepository, SupplyOrderValidationService supplyOrderValidationService, InventoryCatalog inventoryCatalog) {
  public Supply execute(String supplyId) throws DataNotFoundException {
    Supply supply = supplyOrderValidationService.validate(supplyId);

    supply.processSupply();
    supplyRepository.save(supply);

    List<InventoryDetail> inventoryDetails = supply.getDetails()
      .stream()
      .map(this::mapToInventoryDetail)
      .toList();
    inventoryCatalog.updateInventory(supplyId, inventoryDetails);

    return supply;
  }

  private InventoryDetail mapToInventoryDetail(SupplyDetailView detail) {
    InventoryDetail inventoryDetail = new InventoryDetail();
    inventoryDetail.setProductId(detail.getProductId());
    inventoryDetail.setQuantity(detail.getQuantity());
    return inventoryDetail;
  }
}
