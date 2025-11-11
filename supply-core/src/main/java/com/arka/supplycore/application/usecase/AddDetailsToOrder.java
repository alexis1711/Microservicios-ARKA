package com.arka.supplycore.application.usecase;

import com.arka.supplycore.application.dto.AlterDetailOutput;
import com.arka.supplycore.application.dto.FailedProductDto;
import com.arka.supplycore.application.dto.ProcessedProductDTO;
import com.arka.supplycore.application.dto.SupplyInput;
import com.arka.supplycore.application.exception.BusinessException;
import com.arka.supplycore.application.exception.DataNotFoundException;
import com.arka.supplycore.application.service.ProductValidationService;
import com.arka.supplycore.application.service.SupplyOrderValidationService;
import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.repository.SupplyRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record AddDetailsToOrder(SupplyRepository supplyRepository, SupplyOrderValidationService supplyOrderValidationService, ProductValidationService productValidationService) {
  public AlterDetailOutput execute(String supplyId, List<SupplyInput> supplyInputList) throws DataNotFoundException {
    Supply supply = supplyOrderValidationService.validate(supplyId);

    List<SupplyInput> cleanList = productValidationService.validateProductInputList(supplyInputList);

    List<String> idProductList = cleanList.stream().map(SupplyInput::getProductId).toList();

    List<ProcessedProductDTO> addedProducts = new ArrayList<>();
    List<FailedProductDto> failedProducts = cleanList.stream()
      .filter(input -> !idProductList.contains(input.getProductId()))
      .map(input -> new FailedProductDto(input.getProductId(), input.getQuantity(), input.getPrice(), "Product not found"))
      .collect(Collectors.toCollection(ArrayList::new));

    cleanList.forEach(input -> addDetailToSupply(supply, input, addedProducts, failedProducts));
    supplyRepository.save(supply);

    return new AlterDetailOutput(supplyId, addedProducts, failedProducts);
  }

  private void addDetailToSupply(Supply supply, SupplyInput detail, List<ProcessedProductDTO> addedProducts, List<FailedProductDto> failedProducts) {
    Objects.requireNonNull(supply, "supply cannot be null");
    Objects.requireNonNull(detail, "detail cannot be null");
    Objects.requireNonNull(addedProducts, "addedProducts cannot be null");
    Objects.requireNonNull(failedProducts, "failedProducts cannot be null");

    try {
      supply.addDetail(detail);
      addedProducts.add(new ProcessedProductDTO(detail.getProductId(), detail.getQuantity(), detail.getPrice()));
    } catch (IllegalStateException | BusinessException e) {
      failedProducts.add(new FailedProductDto(detail.getProductId(), detail.getQuantity(), detail.getPrice(), e.getMessage()));
    }
  }
}
