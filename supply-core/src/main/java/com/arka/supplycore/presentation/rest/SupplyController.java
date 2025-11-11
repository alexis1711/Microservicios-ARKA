package com.arka.supplycore.presentation.rest;

import com.arka.supplycore.application.dto.AlterDetailOutput;
import com.arka.supplycore.application.dto.FailedProductDto;
import com.arka.supplycore.application.dto.SupplyInput;
import com.arka.supplycore.application.usecase.AddDetailsToOrder;
import com.arka.supplycore.application.usecase.CancelSupplyOrder;
import com.arka.supplycore.application.usecase.DeleteDetailItems;
import com.arka.supplycore.application.usecase.ProcessSupply;
import com.arka.supplycore.application.usecase.RegisterSupplyOrder;
import com.arka.supplycore.application.usecase.UpdateOrderDetails;
import com.arka.supplycore.presentation.dto.ProcessedSupply;
import com.arka.supplycore.presentation.dto.SupplyDetailDTO;
import com.arka.supplycore.presentation.dto.SupplyDetailResponseDTO;
import com.arka.supplycore.presentation.mapper.SupplyControllerMapper;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
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
  private final UpdateOrderDetails updateOrderDetails;
  private final DeleteDetailItems deleteDetailItems;
  private final ProcessSupply processSupply;
  private final CancelSupplyOrder cancelSupplyOrder;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<Map<String, String>>> create() {
    return Mono
      .fromSupplier(registerSupplyOrder::execute)
      .map(id -> ResponseEntity
        .created(URI.create("/api/v1/supply/" + id))
        .body(Map.of("id", id)))
      .subscribeOn(Schedulers.boundedElastic());
  }

  @PostMapping(value = "/{id}/add-products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<SupplyDetailResponseDTO>> addItems(@PathVariable String id, @RequestBody List<SupplyInput> supplyInputList) {
    return Mono
      .fromCallable(() -> addDetailsToOrder.execute(id, supplyInputList))
      .map(rsp -> parseUseCaseResponse(rsp, supplyInputList))
      .subscribeOn(Schedulers.boundedElastic());
  }

  @PatchMapping(value = "/{supplyId}/update-products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<SupplyDetailResponseDTO>> updateItems(@PathVariable String supplyId, @RequestBody SupplyDetailDTO detail) {
    List<SupplyInput> productsInput = detail.getProducts().stream().map(supplyControllerMapper::toDetailItemInput).toList();
    return Mono.fromCallable(() -> updateOrderDetails.execute(supplyId, productsInput))
      .map(rsp -> parseUseCaseResponse(rsp, productsInput))
      .subscribeOn(Schedulers.boundedElastic());
  }

  @PostMapping(value = "/{supplyId}/delete-products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<Void>> deleteItems(@PathVariable String supplyId, @RequestBody SupplyDetailDTO detail) {
    List<SupplyInput> productsInput = detail.getProducts().stream().map(supplyControllerMapper::toDetailItemInput).toList();
    return Mono.fromCallable(() -> {
      deleteDetailItems.execute(supplyId, productsInput);
      return null;
    })
      .subscribeOn(Schedulers.boundedElastic())
      .then(Mono.just(ResponseEntity.ok().build()));

  }

  @PostMapping(value = "/{supplyId}/process", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<ProcessedSupply>> process(@PathVariable String supplyId) {
    return Mono.fromCallable(() -> processSupply.execute(supplyId))
      .map(supplyControllerMapper::toProcessedSupply)
      .map(ResponseEntity::ok)
      .subscribeOn(Schedulers.boundedElastic());
  }

  @PostMapping(value = "/{supplyId}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<Void>> cancel(@PathVariable String supplyId) {
    return Mono.fromCallable(() -> cancelSupplyOrder.execute(supplyId))
      .then(Mono.just(ResponseEntity.ok((Void) null)))
      .subscribeOn(Schedulers.boundedElastic());
  }

  private ResponseEntity<SupplyDetailResponseDTO> parseUseCaseResponse(AlterDetailOutput usecaseRsp, List<SupplyInput> supplyInputList) {
    SupplyDetailResponseDTO rspDto = supplyControllerMapper.toDetailResponse(usecaseRsp);

    if (usecaseRsp.failedProducts() == null || usecaseRsp.failedProducts().isEmpty()) {
      return ResponseEntity.status(HttpStatus.CREATED).body(rspDto);
    }

    List<FailedProductDto> failedProducts = usecaseRsp.failedProducts();

    if (failedProducts.size() == supplyInputList.size()) {
      return ResponseEntity.unprocessableEntity().body(rspDto);
    }

    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(rspDto);
  }
}
