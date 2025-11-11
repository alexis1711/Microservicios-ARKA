package com.arka.sales.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SalesHeadersDto {
    private Long id;
    private LocalDateTime sale_date;
    private String customer_id;
    private String customer_name;
    private String customer_email;
    private double sale_value;
    private int status;
    private int store_id;
    private int provider_id;
}
