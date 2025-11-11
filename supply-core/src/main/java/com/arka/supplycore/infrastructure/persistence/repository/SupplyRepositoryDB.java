package com.arka.supplycore.infrastructure.persistence.repository;

import com.arka.supplycore.infrastructure.persistence.entity.SupplyEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SupplyRepositoryDB extends ReactiveMongoRepository<SupplyEntity, String> {
}

