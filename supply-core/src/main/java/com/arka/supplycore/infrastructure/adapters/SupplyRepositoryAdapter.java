package com.arka.supplycore.infrastructure.adapters;

import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.repository.SupplyRepository;
import com.arka.supplycore.infrastructure.persistence.entity.SupplyDetailEntity;
import com.arka.supplycore.infrastructure.persistence.mapper.SupplyMapper;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SupplyRepositoryAdapter implements SupplyRepository {
  // TODO: agregar repositorio de base de datos
  private final SupplyMapper supplyMapper;

  @Override
  public Optional<Supply> findById(String supplyId) {
    Supply supply = new Supply(supplyId);
    supply.addDetail(new SupplyDetailEntity("test-product", 5, new BigDecimal("7.50")));

    return Optional.of(supply);
  }

  @Override
  public void save(Supply supply) {
    // TODO: Add write to database repo
  }

  @Override
  public void update(Supply supply) {
    // TODO: Add update database
  }

  @Override
  public void delete(Supply supply) {
    // TODO: Delete in database repo
  }
}
