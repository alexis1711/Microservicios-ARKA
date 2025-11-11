package com.arka.supplycore.domain.repository;

import com.arka.supplycore.domain.model.supply.Supply;
import java.util.Optional;

public interface SupplyRepository {
  Optional<Supply> findById(String supplyId);
  void save(Supply supply);
  void delete(Supply supply);
}
