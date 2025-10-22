package com.arka.stores.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {

    private Long id;
    private String name;
    private String telephone;
    private String country;
    private String city;
    private String address;

}
