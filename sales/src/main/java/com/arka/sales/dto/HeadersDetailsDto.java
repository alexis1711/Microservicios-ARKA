package com.arka.sales.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class HeadersDetailsDto {
    private SalesHeadersDto pedido;
    private List<SalesDetailsDto> productos;
}
