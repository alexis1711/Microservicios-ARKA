package com.arka.supplycore.presentation.rest;

import com.arka.supplycore.application.usecase.RegisterSupplyOrder;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequestMapping("/api/v1/supply")
@RequiredArgsConstructor
@RestController
public class SupplyController {
  private final RegisterSupplyOrder registerSupplyOrder;

  @PostMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Mono<ResponseEntity<Map<String, String>>> create() {
    return Mono
      .fromSupplier(registerSupplyOrder::execute)
      .map(id -> ResponseEntity
        .created(URI.create("/api/v1/supply/" + id))
        .body(Map.of("id", id))
      )
      .subscribeOn(Schedulers.boundedElastic());
  }
}
