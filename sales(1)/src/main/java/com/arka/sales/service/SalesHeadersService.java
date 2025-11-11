package com.arka.sales.service;

import com.arka.sales.dto.SalesDetailsDto;
import com.arka.sales.dto.SalesHeadersDto;
import reactor.core.publisher.Mono;

public interface SalesHeadersService {
    Mono<SalesHeadersDto> findById(Long id);
    Mono<SalesHeadersDto> save(SalesHeadersDto salesHeadersDto);
    Mono<SalesHeadersDto> update(Long id, SalesHeadersDto salesHeadersDto);
}
