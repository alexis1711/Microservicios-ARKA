package com.arka.supplycore.presentation.mapper;

import com.arka.supplycore.application.dto.AlterDetailOutput;
import com.arka.supplycore.application.dto.FailedProductDto;
import com.arka.supplycore.application.dto.ProcessedProductDTO;
import com.arka.supplycore.application.dto.SupplyInput;
import com.arka.supplycore.presentation.dto.SupplyDetailItemDTO;
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

  @Mapping(target = "supplyId", source = "alterDetailOutput.supplyId")
  @Mapping(target = "processedProducts", expression = "java(alterDetailOutput.processedProducts().stream().map(SupplyProductDetailResponseDTO::new).toList())")
  @Mapping(target = "failedProducts", expression = "java(alterDetailOutput.failedProducts().stream().map(SupplyProductFailedDetailResponseDTO::new).toList())")
  @Mapping(target = "status", expression = "java(parseStatus(alterDetailOutput.processedProducts(), alterDetailOutput.failedProducts()))")
  SupplyDetailResponseDTO toDetailResponse(AlterDetailOutput alterDetailOutput);

  default String parseStatus(List<ProcessedProductDTO> addedDetails, List<FailedProductDto> failedProducts) {
    if (failedProducts == null || failedProducts.isEmpty()) {
      return "PROCESSED";
    }
    if (addedDetails != null && !addedDetails.isEmpty()) {
      return "SUCCEEDED_WITH_ERRORS";
    }

    return "FAILED";
  }

  SupplyInput toDetailItemInput(SupplyDetailItemDTO supplyDetail);
}
