package com.arka.sales.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;

import java.util.List;

@Getter
@Setter
@Builder
public class HeadersDetailsDto {
    private SalesHeadersDto pedido;
    private Flux<SalesDetailsDto> productos;
}
