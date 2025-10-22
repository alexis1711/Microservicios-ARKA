package com.arka.stores.controller;

import com.arka.stores.dto.ResponseDto;
import com.arka.stores.dto.StockDto;
import com.arka.stores.dto.StoreDto;
import com.arka.stores.service.StockService;
import com.arka.stores.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService service;

    @PostMapping
    public Flux<StockDto> getStock(@RequestBody StockDto stockDto) {
        return service.getStock(stockDto);
    }

    /*@GetMapping("/{id}")
    public Mono<StoreDto> getById(@PathVariable Long id) {
        return service.findById(id);
    }*/

    @PostMapping("/new")
    public Mono<ResponseEntity<ResponseDto>> upsertStock(@RequestBody StockDto stockDto) {
        return service.upsertStock(stockDto)
                .then(Mono.just(ResponseEntity.ok(new ResponseDto("Operacion exitosa", null))))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDto("Error: " + ex.getMessage(), null))));
    }

    @PostMapping("/amount")
    public Mono<ResponseEntity<ResponseDto>> updateStock(@RequestBody StockDto stockDto) {
        return service.updateAmount(stockDto, stockDto.getAmount())
                .then(Mono.just(ResponseEntity.ok(new ResponseDto("Operacion exitosa", null))))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDto("Error: " + ex.getMessage(), null))));
    }
}
