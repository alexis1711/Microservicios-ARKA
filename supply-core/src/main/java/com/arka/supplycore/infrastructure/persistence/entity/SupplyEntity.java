package com.arka.supplycore.infrastructure.persistence.entity;

import com.arka.supplycore.domain.model.supply.SupplyStatus;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "supplies")
public class SupplyEntity {
  @Id
  private String id;
  private BigDecimal total;
  private SupplyStatus status;
  private List<SupplyDetailEntity> details;
}
