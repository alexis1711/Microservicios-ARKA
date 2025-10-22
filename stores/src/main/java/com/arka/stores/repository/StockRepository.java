package com.arka.stores.repository;

import com.arka.stores.dto.StockDto;
import com.arka.stores.entity.Stock;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockRepository {

    Flux<Stock> getStock(Stock stock);
    Mono<Void> upsertStock(Stock stock);
    Mono<Void> updateAmount(Stock stock, int cantidad);

}
