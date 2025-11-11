package com.arka.sales.repository;

import com.arka.sales.entity.SalesHeaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesHeadersRepository extends JpaRepository<SalesHeaders, Long> {

}
