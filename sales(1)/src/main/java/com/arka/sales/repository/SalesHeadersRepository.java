package com.arka.sales.repository;

import com.arka.sales.entity.SalesHeaders;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesHeadersRepository extends ReactiveCrudRepository<SalesHeaders, Long> {
}
