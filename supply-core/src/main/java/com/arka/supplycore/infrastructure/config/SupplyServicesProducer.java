package com.arka.supplycore.infrastructure.config;

import com.arka.supplycore.application.catalog.ProductCatalog;
import com.arka.supplycore.application.service.ProductValidationService;
import com.arka.supplycore.application.service.SupplyOrderValidationService;
import com.arka.supplycore.domain.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SupplyServicesProducer {
  private final SupplyRepository supplyRepository;
  private final ProductCatalog productCatalog;

  @Bean
  public SupplyOrderValidationService generateSupplyValidationService() {
    return new SupplyOrderValidationService(supplyRepository);
  }

  @Bean
  public ProductValidationService generateProductValidationService() {
    return new ProductValidationService(productCatalog);
  }
}
