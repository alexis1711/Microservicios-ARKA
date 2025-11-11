package com.arka.supplycore.application.usecase;

import com.arka.supplycore.application.exception.DataNotFoundException;
import com.arka.supplycore.application.service.SupplyOrderValidationService;
import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.repository.SupplyRepository;

public record CancelSupplyOrder(SupplyRepository supplyRepository, SupplyOrderValidationService supplyOrderValidationService) {
  public Void execute(String supplyId) throws IllegalArgumentException, NullPointerException, DataNotFoundException {
    Supply supply = supplyOrderValidationService.validate(supplyId);
    supply.cancelSupply();
    supplyRepository.save(supply);
    return null;
  }
}
