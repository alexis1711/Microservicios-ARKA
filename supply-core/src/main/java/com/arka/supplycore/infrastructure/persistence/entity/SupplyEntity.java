package com.arka.supplycore.infrastructure.persistence.entity;

import com.arka.supplycore.domain.model.supply.SupplyStatus;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyEntity {
  private String id;
  private BigDecimal total;
  private SupplyStatus status;
  private List<SupplyDetailEntity> details;
}
