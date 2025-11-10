package com.arka.supplycore.presentation.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyDetailDTO {
  private List<SupplyDetailItemDTO> products;
}
