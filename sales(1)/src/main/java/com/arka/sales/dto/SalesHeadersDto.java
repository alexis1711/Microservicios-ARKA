package com.arka.sales.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SalesHeadersDto {
    private Long id;
    private LocalDateTime sale_date;
    private String customer_id;
    private String customer_email;
    private double sale_value;
    private int status;
}
