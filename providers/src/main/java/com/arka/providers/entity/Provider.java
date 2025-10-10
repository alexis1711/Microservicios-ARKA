package com.arka.providers.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("providers")
@Getter
@Setter
@Builder
public class Provider {

    @Id
    private Long id;

    private String name;

    private String email;

    private String telephone;

    private String country;

    private String category;

}
