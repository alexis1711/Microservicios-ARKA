package com.arka.sales.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("sales_headers")
@Getter
@Setter
@Builder
public class SalesHeaders {

    @Id
    private Long id;
    private LocalDateTime sale_date;
    private String customer_id;
    private String customer_email;
    private double sale_value;
    private int status;

}
