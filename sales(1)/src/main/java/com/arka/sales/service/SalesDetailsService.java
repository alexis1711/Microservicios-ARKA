package com.arka.sales.service;

import com.arka.sales.dto.SalesDetailsDto;
import com.arka.sales.entity.SalesDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SalesDetailsService {

    Mono<SalesDetailsDto> findById(Long id);
    Mono<SalesDetailsDto> save(SalesDetailsDto salesDetailsDto);
    Mono<SalesDetailsDto> update(Long id, SalesDetailsDto salesDetailsDto);
    Mono<Void> delete(Long id);
    Flux<SalesDetailsDto> getDetailsByHeader(Long header_id);

}
