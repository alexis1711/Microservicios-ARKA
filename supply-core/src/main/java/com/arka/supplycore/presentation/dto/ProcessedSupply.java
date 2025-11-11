package com.arka.supplycore.presentation.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessedSupply {
  private String supplyId;
  private BigDecimal total;
}
