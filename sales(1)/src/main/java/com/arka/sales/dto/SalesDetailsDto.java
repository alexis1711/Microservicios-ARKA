package com.arka.sales.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SalesDetailsDto {
    private Long id;
    private LocalDateTime sale_date;
    private Long sales_header_id;
    private String product_id;
    private double price;
    private int quantity;
}
