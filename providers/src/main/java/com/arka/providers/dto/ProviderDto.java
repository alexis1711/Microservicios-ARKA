package com.arka.providers.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProviderDto {
    private Long id;

    private String name;

    private String email;

    private String telephone;

    private String country;

    private String category;
}
