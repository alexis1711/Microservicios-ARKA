package com.arka.sales.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class UpdateAmountDto {

    private int store_id;
    private String product_id;
    private int provider_id;
    private int amount;
}
