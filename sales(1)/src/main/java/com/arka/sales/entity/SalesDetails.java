package com.arka.sales.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("sales_details")
@Getter
@Setter
@Builder
public class SalesDetails {

    @Id
    private Long id;
    private LocalDateTime sale_date;
    private Long sales_header_id;
    private String product_id;
    private double price;
    private int quantity;

}
