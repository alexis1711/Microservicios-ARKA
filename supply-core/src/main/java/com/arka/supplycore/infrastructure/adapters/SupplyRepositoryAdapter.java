package com.arka.supplycore.infrastructure.adapters;

import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.repository.SupplyRepository;
import com.arka.supplycore.infrastructure.persistence.entity.SupplyEntity;
import com.arka.supplycore.infrastructure.persistence.mapper.SupplyMapper;
import com.arka.supplycore.infrastructure.persistence.repository.SupplyRepositoryDB;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SupplyRepositoryAdapter implements SupplyRepository {
  private final SupplyRepositoryDB supplyRepositoryDB;
  private final SupplyMapper supplyMapper;

  @Override
  public Optional<Supply> findById(String supplyId) {
    SupplyEntity entity = supplyRepositoryDB.findById(supplyId).block(Duration.ofSeconds(3));

    return Optional.ofNullable(supplyMapper.toDomain(entity));
  }

  @Override
  public void save(Supply supply) {
    SupplyEntity entity = supplyMapper.toDatabase(supply);
    supplyRepositoryDB.save(entity).block(Duration.ofSeconds(5));
  }

  @Override
  public void delete(Supply supply) {
    SupplyEntity entity = supplyMapper.toDatabase(supply);
    supplyRepositoryDB.delete(entity).block(Duration.ofSeconds(5));
  }
}
