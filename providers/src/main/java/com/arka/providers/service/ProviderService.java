package com.arka.providers.service;

import com.arka.providers.dto.ProviderDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProviderService {
    Flux<ProviderDto> findAll();
    Mono<ProviderDto> findById(Long id);
    Mono<ProviderDto> save(ProviderDto providerDto);
    Mono<ProviderDto> update(Long id, ProviderDto providerDto);
    Mono<Void> inactive(Long id);
}
