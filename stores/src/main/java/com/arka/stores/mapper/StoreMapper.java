package com.arka.stores.mapper;

import com.arka.stores.dto.StoreDto;
import com.arka.stores.entity.Store;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {
    public Store toEntity(StoreDto dto) {
        return Store.builder()
                .id(dto.getId())
                .name(dto.getName())
                .telephone(dto.getTelephone())
                .country(dto.getCountry())
                .city(dto.getCity())
                .address(dto.getAddress())
                .build();
    }

    public StoreDto toDto(Store entity) {
        return StoreDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .telephone(entity.getTelephone())
                .country(entity.getCountry())
                .city(entity.getCity())
                .address(entity.getAddress())
                .build();
    }
}
