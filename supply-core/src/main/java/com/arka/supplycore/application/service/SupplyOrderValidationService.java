package com.arka.supplycore.application.service;

import com.arka.supplycore.application.exception.DataNotFoundException;
import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.repository.SupplyRepository;
import java.util.Optional;

public record SupplyOrderValidationService (SupplyRepository supplyRepository){
  public Supply validate(String supplyId) throws IllegalArgumentException, DataNotFoundException {
    if (supplyId == null || supplyId.isBlank()) {
      throw new IllegalArgumentException("Supply id cannot be null or blank");
    }

    Optional<Supply> optionalSupply = supplyRepository.findById(supplyId);

    if (optionalSupply.isEmpty()) {
      throw new DataNotFoundException("The supply with ID " + supplyId + " cannot be found");
    }

    return optionalSupply.get();
  }
}
