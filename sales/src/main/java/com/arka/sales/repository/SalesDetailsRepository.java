package com.arka.sales.repository;

import com.arka.sales.entity.SalesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesDetailsRepository extends JpaRepository<SalesDetails, Long> {
    List<SalesDetails> findBySalesHeaderId(Long salesHeaderId);

}
