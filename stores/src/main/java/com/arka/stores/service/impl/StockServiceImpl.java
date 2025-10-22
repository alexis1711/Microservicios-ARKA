package com.arka.stores.service.impl;

import com.arka.stores.dto.StockDto;
import com.arka.stores.entity.Stock;
import com.arka.stores.mapper.StockMapper;
import com.arka.stores.repository.StockRepository;
import com.arka.stores.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository repository;
    private final StockMapper mapper;

    @Override
    public Flux<StockDto> getStock(StockDto stockDto) {
        Stock stock = mapper.toEntity(stockDto);
        return repository.getStock(stock).map(mapper::toDto);
    }

    @Override
    public Mono<Void> upsertStock(StockDto stockDto) {
        Stock stock = mapper.toEntity(stockDto);
        return repository.upsertStock(stock);
    }

    @Override
    public Mono<Void> updateAmount(StockDto stockDto, int cantidad) {
        Stock stock = mapper.toEntity(stockDto);
        return repository.updateAmount(stock, cantidad);
    }
}
