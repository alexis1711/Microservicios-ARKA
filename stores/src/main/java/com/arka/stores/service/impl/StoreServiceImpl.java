package com.arka.stores.service.impl;

import com.arka.stores.dto.StoreDto;
import com.arka.stores.entity.Store;
import com.arka.stores.mapper.StoreMapper;
import com.arka.stores.repository.StoreRepository;
import com.arka.stores.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository repository;
    private final StoreMapper mapper;

    @Override
    public Flux<StoreDto> findAll() {
        return repository.findAll().map(mapper::toDto);
    }

    @Override
    public Mono<StoreDto> findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Store not found")));
    }

    @Override
    public Mono<StoreDto> save(StoreDto storeDto) {
        Store store = mapper.toEntity(storeDto);
        return repository.save(store).map(mapper::toDto);
    }

    @Override
    public Mono<StoreDto> update(Long id, StoreDto storeDto) {
        return repository.findById(id).flatMap(existing -> {
            existing.setName(storeDto.getName());
            existing.setTelephone(storeDto.getTelephone());
            existing.setCountry(storeDto.getCountry());
            existing.setCity(storeDto.getCity());
            existing.setAddress(storeDto.getAddress());
            return repository.save(existing);
        }).map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Store not found")));
    }
}
