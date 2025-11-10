package com.arka.supplycore.application.usecase;

import com.arka.supplycore.application.dto.AlterDetailOutput;
import com.arka.supplycore.application.dto.FailedProductDto;
import com.arka.supplycore.application.dto.ProcessedProductDTO;
import com.arka.supplycore.application.dto.SupplyInput;
import com.arka.supplycore.application.exception.DataNotFoundException;
import com.arka.supplycore.application.service.ProductValidationService;
import com.arka.supplycore.application.service.SupplyOrderValidationService;
import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.repository.SupplyRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record UpdateOrderDetails(SupplyRepository supplyRepository, SupplyOrderValidationService supplyOrderValidationService, ProductValidationService productValidationService) {
  public AlterDetailOutput execute(String supplyId, List<SupplyInput> supplyInputList) throws DataNotFoundException, IllegalArgumentException, NullPointerException {
    Supply supply = supplyOrderValidationService.validate(supplyId);
    List<SupplyInput> cleanList = productValidationService.validateProductInputList(supplyInputList);

    List<SupplyInput> existingProducts = productValidationService.validateProductInputList(cleanList);

    List<String> idProductList = existingProducts.stream().map(SupplyInput::getProductId).toList();

    List<ProcessedProductDTO> updatedProducts = new ArrayList<>();
    List<FailedProductDto> failedProducts = cleanList.stream()
      .filter(input -> !idProductList.contains(input.getProductId()))
      .map(input -> new FailedProductDto(input.getProductId(), input.getQuantity(), input.getPrice(), "Product not found"))
      .collect(Collectors.toCollection(ArrayList::new));

    existingProducts.forEach(input -> updateSupplyProduct(supply, input, updatedProducts, failedProducts));
    supplyRepository.save(supply);
    return new AlterDetailOutput(supplyId, updatedProducts, failedProducts);
  }

  private void updateSupplyProduct(Supply supply, SupplyInput detail, List<ProcessedProductDTO> updatedProducts, List<FailedProductDto> failedProducts) {
    Objects.requireNonNull(supply, "supply cannot be null");
    Objects.requireNonNull(updatedProducts, "updatedProducts cannot be null");
    Objects.requireNonNull(failedProducts, "failedProducts cannot be null");

    try {
      supply.updateDetail(detail);
      updatedProducts.add(new ProcessedProductDTO(detail.getProductId(), detail.getQuantity(), detail.getPrice()));
    } catch (IllegalStateException e) {
      failedProducts.add(new FailedProductDto(detail.getProductId(), detail.getQuantity(), detail.getPrice(), e.getMessage()));
    }
  }
}
