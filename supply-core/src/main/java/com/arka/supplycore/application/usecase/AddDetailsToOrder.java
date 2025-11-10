package com.arka.supplycore.application.usecase;

import com.arka.supplycore.application.catalog.ProductCatalog;
import com.arka.supplycore.application.dto.AddDetailOutput;
import com.arka.supplycore.application.dto.FailedProductDto;
import com.arka.supplycore.application.dto.ProcessedProductDTO;
import com.arka.supplycore.application.dto.SupplyInput;
import com.arka.supplycore.application.exception.BusinessException;
import com.arka.supplycore.application.exception.DataNotFoundException;
import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.repository.SupplyRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record AddDetailsToOrder(SupplyRepository supplyRepository, ProductCatalog productCatalog) {
  public AddDetailOutput execute(String supplyId, List<SupplyInput> supplyInputList) throws DataNotFoundException {
    if (supplyId == null || supplyId.isBlank()) {
      throw new IllegalArgumentException("Supply id cannot be null or blank");
    }

    List<SupplyInput> cleanList = Objects.requireNonNull(supplyInputList, "details cannot be null");

    if (cleanList.isEmpty()) {
      throw new IllegalArgumentException("details cannot be empty");
    }

    Optional<Supply> optionalSupply = supplyRepository.findById(supplyId);

    if (optionalSupply.isEmpty()) {
      throw new DataNotFoundException();
    }

    Supply supply = optionalSupply.get();

    List<ProcessedProductDTO> addedProducts = new ArrayList<>();
    List<FailedProductDto> failedProducts = new ArrayList<>();

    cleanList.forEach(input -> addDetailToSupply(supply, input, addedProducts, failedProducts));
    supplyRepository.save(supply);

    return new AddDetailOutput(supplyId, addedProducts, failedProducts);
  }

  private void addDetailToSupply(Supply supply, SupplyInput detail, List<ProcessedProductDTO> addedProducts, List<FailedProductDto> failedProducts) {
    Objects.requireNonNull(supply, "supply cannot be null");
    Objects.requireNonNull(detail, "detail cannot be null");
    Objects.requireNonNull(addedProducts, "addedProducts cannot be null");
    Objects.requireNonNull(failedProducts, "failedProducts cannot be null");

    if (!productCatalog.exists(detail.getProductId())) {
      failedProducts.add(new FailedProductDto(detail.getProductId(), detail.getQuantity(), detail.getPrice(), "Product not found"));
      return;
    }

    try {
      supply.addDetail(detail);
      addedProducts.add(new ProcessedProductDTO(detail.getProductId(), detail.getQuantity(), detail.getPrice()));
    } catch (IllegalStateException | BusinessException e) {
      failedProducts.add(new FailedProductDto(detail.getProductId(), detail.getQuantity(), detail.getPrice(), e.getMessage()));
    }
  }
}
