package com.arka.stores.controller;

import com.arka.stores.dto.StoreDto;
import com.arka.stores.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService service;

    @GetMapping
    public Flux<StoreDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<StoreDto> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Mono<StoreDto> create(@RequestBody StoreDto storeDto) {
        return service.save(storeDto);
    }

    @PutMapping("/{id}")
    public Mono<StoreDto> update(@PathVariable Long id, @RequestBody StoreDto storeDto) {
        return service.update(id, storeDto);
    }

}
