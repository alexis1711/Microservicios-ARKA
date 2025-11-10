package com.arka.supplycore.infrastructure.config;

import com.arka.supplycore.application.catalog.ProductCatalog;
import com.arka.supplycore.application.usecase.AddDetailsToOrder;
import com.arka.supplycore.application.usecase.RegisterSupplyOrder;
import com.arka.supplycore.domain.commons.IdGenerator;
import com.arka.supplycore.domain.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SupplyUseCaseProducer {
  private final SupplyRepository supplyRepository;
  private final IdGenerator idGenerator;
  private final ProductCatalog productCatalog;

  @Bean
  public RegisterSupplyOrder registerSupplyOrder() {
    return new RegisterSupplyOrder(supplyRepository, idGenerator);
  }

  @Bean
  public AddDetailsToOrder generateAddDetailsUseCase() {
    return new AddDetailsToOrder(supplyRepository, productCatalog);
  }
}
