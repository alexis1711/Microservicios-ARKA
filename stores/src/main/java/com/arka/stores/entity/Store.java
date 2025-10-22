package com.arka.stores.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("store")
public class Store {

    @Id
    private Long id;
    private String name;
    private String telephone;
    private String country;
    private String city;
    private String address;

}
