package com.arka.providers.repository;

import com.arka.providers.entity.Provider;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends ReactiveCrudRepository<Provider, Long> {

}
