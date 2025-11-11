package com.arka.supplycore.application.usecase;

import com.arka.supplycore.domain.commons.IdGenerator;
import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;

/**
 * Application use case to register a new Supply order
 */
@RequiredArgsConstructor
public class RegisterSupplyOrder {
  private final SupplyRepository supplyRepository;
  private final IdGenerator idGenerator;

  public String execute() {
    Supply supply = new Supply(idGenerator.generateId());
    supplyRepository.save(supply);
    return supply.getSupplyId();
  }
}
