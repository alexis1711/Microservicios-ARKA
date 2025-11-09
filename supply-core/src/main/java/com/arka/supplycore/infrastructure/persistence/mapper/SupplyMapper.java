package com.arka.supplycore.infrastructure.persistence.mapper;

import com.arka.supplycore.domain.model.supply.Supply;
import com.arka.supplycore.infrastructure.persistence.entity.SupplyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SupplyMapper {
  SupplyMapper INSTANCE = Mappers.getMapper(SupplyMapper.class);

  @Mapping(target = "id", source = "supplyId")
  SupplyEntity toDatabase(Supply supply);

  // ignore map of details, it's added in mapToDomain
  @Mapping(target = "details", ignore = true)
  Supply toDomain(SupplyEntity supplyEntity);

  @ObjectFactory
  default Supply mapToDomain(SupplyEntity supplyEntity) {
    Supply supply = new Supply(supplyEntity.getId(), supplyEntity.getStatus());
    supplyEntity.getDetails().forEach(supply::addDetail);

    return supply;
  }
}
