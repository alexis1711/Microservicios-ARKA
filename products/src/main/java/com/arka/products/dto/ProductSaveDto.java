package com.arka.products.dto;

import com.arka.products.repository.ProductRepository;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Getter
@Setter
public class ProductSaveDto {
    private String mensaje;
    private ProductDto productDto;
}
