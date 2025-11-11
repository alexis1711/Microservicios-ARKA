package com.arka.sales.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales_details")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SalesDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime sale_date;
    @Column(name = "sales_header_id")
    private Long salesHeaderId;
    private String product_id;
    private double price;
    private int quantity;
}
