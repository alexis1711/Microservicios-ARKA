package com.arka.stores.service;

import com.arka.stores.dto.StockDto;
import com.arka.stores.entity.Stock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockService {
    Flux<StockDto> getStock(StockDto stockDto);
    Mono<Void> upsertStock(StockDto stockDto);
    Mono<Void> updateAmount(StockDto stockDto, int cantidad);
}
