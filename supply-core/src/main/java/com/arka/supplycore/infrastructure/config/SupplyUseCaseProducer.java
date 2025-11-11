package com.arka.supplycore.infrastructure.config;

import com.arka.supplycore.application.catalog.InventoryCatalog;
import com.arka.supplycore.application.service.ProductValidationService;
import com.arka.supplycore.application.service.SupplyOrderValidationService;
import com.arka.supplycore.application.usecase.AddDetailsToOrder;
import com.arka.supplycore.application.usecase.DeleteDetailItems;
import com.arka.supplycore.application.usecase.ProcessSupply;
import com.arka.supplycore.application.usecase.RegisterSupplyOrder;
import com.arka.supplycore.application.usecase.UpdateOrderDetails;
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
  private final SupplyOrderValidationService supplyOrderValidationService;
  private final ProductValidationService productValidationService;
  private final InventoryCatalog inventoryCatalog;

  @Bean
  public RegisterSupplyOrder registerSupplyOrder() {
    return new RegisterSupplyOrder(supplyRepository, idGenerator);
  }

  @Bean
  public AddDetailsToOrder generateAddDetailsUseCase() {
    return new AddDetailsToOrder(supplyRepository, supplyOrderValidationService, productValidationService);
  }

  @Bean
  public UpdateOrderDetails updateOrderDetails() {
    return new UpdateOrderDetails(supplyRepository, supplyOrderValidationService, productValidationService);
  }

  @Bean
  public DeleteDetailItems deleteDetailItems() {
    return new DeleteDetailItems(supplyRepository, supplyOrderValidationService);
  }

  @Bean
  public ProcessSupply processSupply() {
    return new ProcessSupply(supplyRepository, supplyOrderValidationService, inventoryCatalog);
  }
}
