package com.arka.supplycore.presentation.rest;

import com.arka.supplycore.application.dto.FailedProductDto;
import com.arka.supplycore.application.dto.SupplyInput;
import com.arka.supplycore.application.usecase.AddDetailsToOrder;
import com.arka.supplycore.application.usecase.RegisterSupplyOrder;
import com.arka.supplycore.presentation.dto.SupplyDetailResponseDTO;
import com.arka.supplycore.presentation.mapper.SupplyControllerMapper;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequestMapping("/api/v1/supply")
@RequiredArgsConstructor
@RestController
public class SupplyController {
  private final RegisterSupplyOrder registerSupplyOrder;
  private final AddDetailsToOrder addDetailsToOrder;
  private final SupplyControllerMapper supplyControllerMapper;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<Map<String, String>>> create() {
    return Mono
      .fromSupplier(registerSupplyOrder::execute)
      .map(id -> ResponseEntity
        .created(URI.create("/api/v1/supply/" + id))
        .body(Map.of("id", id))
      )
      .subscribeOn(Schedulers.boundedElastic());
  }

  @PostMapping(value = "/{id}/details", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<SupplyDetailResponseDTO>> addItems(@PathVariable String id, @RequestBody List<SupplyInput> supplyInputList) {
    return Mono
      .fromCallable(() -> addDetailsToOrder.execute(id, supplyInputList))
      .map(rsp -> {
        SupplyDetailResponseDTO rspDto = supplyControllerMapper.toDetailResponse(rsp);

        if (rsp.failedProducts() == null || rsp.failedProducts().isEmpty()) {
          return ResponseEntity.status(HttpStatus.CREATED).body(rspDto);
        }

        List<FailedProductDto> failedProducts = rsp.failedProducts();

        if (failedProducts.size() == supplyInputList.size()) {
          return ResponseEntity.unprocessableEntity().body(rspDto);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(rspDto);
      })
      .subscribeOn(Schedulers.boundedElastic());
  }
}
