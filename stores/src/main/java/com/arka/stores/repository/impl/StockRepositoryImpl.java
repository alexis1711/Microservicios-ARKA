package com.arka.stores.repository.impl;

import com.arka.stores.dto.StockDto;
import com.arka.stores.entity.Stock;
import com.arka.stores.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final DatabaseClient client;

    @Override
    public Flux<Stock> getStock(Stock stock) {
        return client
                .sql("CALL get_stock(:store_id, :product_id, :provider_id)")
                .bind("store_id", Parameter.fromOrEmpty(stock.getStore_id(), Long.class))
                .bind("product_id", Parameter.fromOrEmpty(stock.getProduct_id(), String.class))
                .bind("provider_id", Parameter.fromOrEmpty(stock.getProvider_id(), Long.class))
                .map((row, metadata) ->
                {
                    Stock stock1 = new Stock();
                    stock1.setId(row.get("id", Long.class));
                    stock1.setStore_id(row.get("store_id", Long.class));
                    stock1.setProduct_id(row.get("product_id", String.class));
                    stock1.setProvider_id(row.get("provider_id", Long.class));
                    stock1.setAmount(row.get("amount", Integer.class));
                    stock1.setUpdate_date(row.get("update_date", java.time.LocalDateTime.class));
                    return stock1;
                })
                .all();
    }

    @Override
    public Mono<Void> upsertStock(Stock stock) {
        return client
                .sql("CALL upsert_stock(:store_id, :product_id, :provider_id, :amount)")
                .bind("store_id", stock.getStore_id())
                .bind("product_id", stock.getProduct_id())
                .bind("provider_id", stock.getProvider_id())
                .bind("amount", stock.getAmount())
                .then();
    }

    @Override
    public Mono<Void> updateAmount(Stock stock, int cantidad) {
        return client
                .sql("CALL update_stock_amount(:store_id, :product_id, :provider_id, :cantidad)")
                .bind("store_id", stock.getStore_id())
                .bind("product_id", stock.getProduct_id())
                .bind("provider_id", stock.getProvider_id())
                .bind("cantidad", cantidad)
                .then();
    }
}
