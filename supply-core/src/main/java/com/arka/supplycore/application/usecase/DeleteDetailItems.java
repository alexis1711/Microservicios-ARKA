package com.arka.supplycore.application.usecase;

import com.arka.supplycore.application.dto.SupplyInput;
import com.arka.supplycore.application.exception.BusinessException;
import com.arka.supplycore.application.exception.DataNotFoundException;
import com.arka.supplycore.application.service.SupplyOrderValidationService;
import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.repository.SupplyRepository;
import java.util.List;

public record DeleteDetailItems(SupplyRepository supplyRepository, SupplyOrderValidationService supplyOrderValidationService) {
  public void execute(String supplyId, List<SupplyInput> supplyInputList) throws DataNotFoundException, IllegalArgumentException, NullPointerException {
    Supply supply = supplyOrderValidationService.validate(supplyId);

    supplyInputList.forEach(detail -> removeDetail(supply, detail));
    supplyRepository.save(supply);
  }

  private void removeDetail(Supply supply, SupplyInput detail) {
    try {
      supply.removeDetail(detail);
    } catch (IllegalStateException | BusinessException e) {
      // Do nothing because of remove, there's nothing to change
    }
  }
}
