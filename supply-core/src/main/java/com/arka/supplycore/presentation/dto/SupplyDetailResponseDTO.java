package com.arka.supplycore.presentation.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyDetailResponseDTO {
  private String supplyId;
  private String status;
  private List<SupplyProductDetailResponseDTO> processedProducts = new ArrayList<>();
  private List<SupplyProductFailedDetailResponseDTO> failedProducts = new ArrayList<>();
}
