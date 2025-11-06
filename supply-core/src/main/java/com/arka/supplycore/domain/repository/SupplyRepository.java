package com.arka.supplycore.domain.repository;

import com.arka.supplycore.domain.model.supply.Supply;

public interface SupplyRepository {
  Supply findById(String supplyId);
  void save(Supply supply);
  void update(Supply supply);
  void delete(Supply supply);
}
