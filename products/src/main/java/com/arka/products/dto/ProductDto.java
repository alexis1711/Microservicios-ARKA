package com.arka.products.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean status;
}
