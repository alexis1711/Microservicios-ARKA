package com.arka.providers.mapper;

import com.arka.providers.dto.ProviderDto;
import com.arka.providers.entity.Provider;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper {
    public ProviderDto toDto(Provider entity) {
        if (entity == null) return null;
        return ProviderDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .telephone(entity.getTelephone())
                .country(entity.getCountry())
                .category(entity.getCategory())
                .build();
    }

    public Provider toEntity(ProviderDto dto) {
        if (dto == null) return null;
        return Provider.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .telephone(dto.getTelephone())
                .country(dto.getCountry())
                .category(dto.getCategory())
                .build();
    }
}
