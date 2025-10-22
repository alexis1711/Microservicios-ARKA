package com.arka.stores.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @Id
    private Long id;
    private Long store_id;
    private String product_id;
    private Long provider_id;
    private int amount;
    private LocalDateTime update_date;

}
