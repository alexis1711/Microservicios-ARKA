package com.arka.stores.service;

import com.arka.stores.dto.StoreDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StoreService {
    Flux<StoreDto> findAll();
    Mono<StoreDto> findById(Long id);
    Mono<StoreDto> save(StoreDto storeDto);
    Mono<StoreDto> update(Long id, StoreDto storeDto);
}
