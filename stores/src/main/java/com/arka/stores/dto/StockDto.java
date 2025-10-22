package com.arka.stores.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDto {

    private Long id;
    private Long store_id;
    private String product_id;
    private Long provider_id;
    private int amount;
    private LocalDateTime update_date;

}
