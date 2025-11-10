package com.arka.supplycore.presentation.mapper;

import com.arka.supplycore.application.dto.AddDetailOutput;
import com.arka.supplycore.application.dto.FailedProductDto;
import com.arka.supplycore.application.dto.ProcessedProductDTO;
import com.arka.supplycore.presentation.dto.SupplyDetailResponseDTO;
import com.arka.supplycore.presentation.dto.SupplyProductDetailResponseDTO;
import com.arka.supplycore.presentation.dto.SupplyProductFailedDetailResponseDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = {SupplyProductDetailResponseDTO.class, SupplyProductFailedDetailResponseDTO.class, ProcessedProductDTO.class})
public interface SupplyControllerMapper {
  SupplyControllerMapper INSTANCE = Mappers.getMapper(SupplyControllerMapper.class);

  @Mapping(target = "supplyId", source = "addDetailOutput.supplyId")
  @Mapping(target = "processedProducts", expression = "java(addDetailOutput.processedProducts().stream().map(SupplyProductDetailResponseDTO::new).toList())")
  @Mapping(target = "failedProducts", expression = "java(addDetailOutput.failedProducts().stream().map(SupplyProductFailedDetailResponseDTO::new).toList())")
  @Mapping(target = "status", expression = "java(parseStatus(addDetailOutput.processedProducts(), addDetailOutput.failedProducts()))")
  SupplyDetailResponseDTO toDetailResponse(AddDetailOutput addDetailOutput);

  default String parseStatus(List<ProcessedProductDTO> addedDetails, List<FailedProductDto> failedProducts) {
    if (failedProducts == null || failedProducts.isEmpty()) {
      return "PROCESSED";
    }
    if (addedDetails != null && !addedDetails.isEmpty()) {
      return "SUCCEEDED_WITH_ERRORS";
    }

    return "FAILED";
  }
}
