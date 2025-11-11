package com.arka.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private String id;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;
    @Positive(message = "El precio debe ser un número positivo")
    private double price;
    @NotBlank(message = "La categoría no puede estar vacía")
    private String category;
    private boolean status;
}
