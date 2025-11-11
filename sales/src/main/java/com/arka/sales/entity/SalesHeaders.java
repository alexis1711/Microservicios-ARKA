package com.arka.sales.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales_headers")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SalesHeaders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime sale_date;
    private String customer_id;
    private String customer_name;
    private String customer_email;
    private double sale_value;
    private int status;
    private int store_id;
    private int provider_id;
}
