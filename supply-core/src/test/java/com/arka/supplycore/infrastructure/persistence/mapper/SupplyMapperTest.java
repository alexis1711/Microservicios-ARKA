package com.arka.supplycore.infrastructure.persistence.mapper;

import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.model.supply.SupplyDetailView;
import com.arka.supplycore.domain.model.supply.SupplyStatus;
import com.arka.supplycore.infrastructure.persistence.entity.SupplyDetailEntity;
import com.arka.supplycore.infrastructure.persistence.entity.SupplyEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyMapperTest {

  record Detail(String productId, int quantity, BigDecimal price) implements SupplyDetailView {
    @Override
    public String getProductId() {
      return productId;
    }

    @Override
    public int getQuantity() {
      return quantity;
    }

    @Override
    public BigDecimal getPrice() {
      return price;
    }
  }

  @Test
  void toDatabase() {
    Supply supply = new Supply("id1");
    supply.addDetail(new Detail("product1", 1, BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP)));
    supply.addDetail(new Detail("product2", 2, BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP)));

    SupplyEntity entity = SupplyMapper.INSTANCE.toDatabase(supply);

    Assertions.assertNotNull(entity);
    Assertions.assertEquals(supply.getSupplyId(), entity.getId());
    Assertions.assertEquals(supply.getTotal(), entity.getTotal());
    Assertions.assertEquals(supply.getDetails().size(), entity.getDetails().size());
    IntStream.range(0, supply.getDetails().size())
      .forEach(i -> {
        SupplyDetailView supplyDetailView = supply.getDetails().get(i);
        SupplyDetailEntity detailEntity = entity.getDetails()
          .stream()
          .filter(o -> o.getProductId().equalsIgnoreCase(supplyDetailView.getProductId()))
          .findFirst()
          .orElse(null);

        Assertions.assertNotNull(detailEntity);
        Assertions.assertEquals(supplyDetailView.getQuantity(), detailEntity.getQuantity());
        Assertions.assertEquals(supplyDetailView.getPrice(), detailEntity.getPrice());
      });
  }

  @Test
  void toDomain() {
    SupplyEntity supplyEntity = new SupplyEntity();
    supplyEntity.setId("entity-1");
    supplyEntity.setTotal(BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP));
    supplyEntity.setStatus(SupplyStatus.CREATED);

    List<SupplyDetailEntity> details = new ArrayList<>();
    details.add(new SupplyDetailEntity("product1", 1, BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP)));
    details.add(new SupplyDetailEntity("product2", 2, BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP)));
    supplyEntity.setDetails(details);

    Supply supply = SupplyMapper.INSTANCE.toDomain(supplyEntity);

    Assertions.assertNotNull(supply);
    Assertions.assertEquals(supply.getSupplyId(), supplyEntity.getId());
    Assertions.assertEquals(supply.getTotal(), supplyEntity.getTotal());
    Assertions.assertEquals(supply.getDetails().size(), supplyEntity.getDetails().size());
    IntStream.range(0, supply.getDetails().size())
      .forEach(i -> {
        SupplyDetailEntity detailEntity = details.get(i);
        SupplyDetailView supplyDetailView = supply.getDetails().stream().filter(o -> o.getProductId().equalsIgnoreCase(detailEntity.getProductId())).findFirst().orElse(null);

        Assertions.assertNotNull(supplyDetailView);
        Assertions.assertEquals(detailEntity.getQuantity(), supplyDetailView.getQuantity());
        Assertions.assertEquals(detailEntity.getPrice(), supplyDetailView.getPrice());
      });
  }
}
