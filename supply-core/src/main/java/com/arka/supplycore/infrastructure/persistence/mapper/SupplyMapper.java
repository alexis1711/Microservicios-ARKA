package com.arka.supplycore.infrastructure.persistence.mapper;

import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.domain.model.supply.SupplyDetailView;
import com.arka.supplycore.infrastructure.persistence.entity.SupplyDetailEntity;
import com.arka.supplycore.infrastructure.persistence.entity.SupplyEntity;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", imports = {SupplyDetailEntity.class})
public interface SupplyMapper {
  SupplyMapper INSTANCE = Mappers.getMapper(SupplyMapper.class);

  @Mapping(target = "id", source = "supplyId")
  @Mapping(target = "details", expression = "java(supply.getDetails().stream().map(SupplyDetailEntity::new).toList())")
  SupplyEntity toDatabase(Supply supply);

  @Mapping(target = "details", ignore = true)
  @ObjectFactory
  default Supply toDomain(SupplyEntity supplyEntity){
    return new Supply(supplyEntity.getId(), supplyEntity.getStatus(), supplyEntity.getTotal(),
      supplyEntity.getDetails().stream().map(SupplyDetailView.class::cast).collect(Collectors.toCollection(ArrayList::new)));
  }
}
