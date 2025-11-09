package com.arka.supplycore.infrastructure.adapters;

import com.arka.supplycore.domain.commons.IdGenerator;
import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorImpl implements IdGenerator {
  @Override
  public String generateId() {
    return Generators.timeBasedEpochRandomGenerator().generate().toString();
  }
}
