package com.arka.providers.controller;

import com.arka.providers.dto.ProviderDto;
import com.arka.providers.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping
    public Flux<ProviderDto> getAll() {
        return providerService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ProviderDto> getById(@PathVariable Long id) {
        return providerService.findById(id);
    }

    @PostMapping
    public Mono<ProviderDto> create(@RequestBody ProviderDto proveedorDto) {
        return providerService.save(proveedorDto);
    }

    @PutMapping("/{id}")
    public Mono<ProviderDto> update(@PathVariable Long id, @RequestBody ProviderDto proveedorDto) {
        return providerService.update(id, proveedorDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return providerService.inactive(id);
    }

}
