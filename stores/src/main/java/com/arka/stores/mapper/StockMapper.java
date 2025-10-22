package com.arka.stores.mapper;

import com.arka.stores.dto.StockDto;
import com.arka.stores.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public Stock toEntity(StockDto dto){

        return Stock.builder()
                .id(dto.getId())
                .store_id(dto.getStore_id() == null ? 0 : dto.getStore_id())
                .product_id(dto.getProduct_id() == null ? "" : dto.getProduct_id())
                .provider_id(dto.getProvider_id() == null ? 0 : dto.getProvider_id())
                .amount(dto.getAmount())
                .update_date(dto.getUpdate_date())
                .build();
    }

    public StockDto toDto(Stock entity){
        return StockDto.builder()
                .id(entity.getId())
                .store_id(entity.getStore_id())
                .product_id(entity.getProduct_id())
                .provider_id(entity.getProvider_id())
                .amount(entity.getAmount())
                .update_date(entity.getUpdate_date())
                .build();
    }

}
