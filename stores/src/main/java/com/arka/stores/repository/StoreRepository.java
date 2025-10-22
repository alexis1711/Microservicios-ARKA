package com.arka.stores.repository;

import com.arka.stores.entity.Store;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends ReactiveCrudRepository<Store, Long> {
}
