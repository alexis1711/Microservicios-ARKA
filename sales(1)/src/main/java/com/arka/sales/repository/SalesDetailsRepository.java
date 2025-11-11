package com.arka.sales.repository;

import com.arka.sales.entity.SalesDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SalesDetailsRepository extends ReactiveCrudRepository<SalesDetails, Long> {
    @Query("SELECT * FROM sales_details WHERE sale_header_id = :saleHeaderId")
    Flux<SalesDetails> findBySale_Header_Id(Long saleHeaderId);
}
