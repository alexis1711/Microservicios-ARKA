package com.arka.sales.mapper;

import com.arka.sales.dto.SalesDetailsDto;
import com.arka.sales.dto.SalesHeadersDto;
import com.arka.sales.entity.SalesDetails;
import com.arka.sales.entity.SalesHeaders;
import org.springframework.stereotype.Component;

@Component
public class SalesMapper {

    public SalesHeadersDto headersToDTO(SalesHeaders headers){
        if (headers == null) return null;
        return SalesHeadersDto.builder()
                .id(headers.getId())
                .sale_date(headers.getSale_date())
                .customer_id(headers.getCustomer_id())
                .customer_email(headers.getCustomer_email())
                .sale_value(headers.getSale_value())
                .status(headers.getStatus())
                .build();
    }

    public SalesDetailsDto detailsToDTO(SalesDetails details){
        if (details == null) return null;
        return SalesDetailsDto.builder()
                .id(details.getId())
                .sale_date(details.getSale_date())
                .sales_header_id(details.getSales_header_id())
                .product_id(details.getProduct_id())
                .price(details.getPrice())
                .quantity(details.getQuantity())
                .build();
    }

    public SalesHeaders headersToEntity(SalesHeadersDto headers){
        if (headers == null) return null;
        return SalesHeaders.builder()
                .id(headers.getId())
                .sale_date(headers.getSale_date())
                .customer_id(headers.getCustomer_id())
                .customer_email(headers.getCustomer_email())
                .sale_value(headers.getSale_value())
                .status(headers.getStatus())
                .build();
    }

    public SalesDetails detailsToEntity(SalesDetailsDto details){
        if (details == null) return null;
        return SalesDetails.builder()
                .id(details.getId())
                .sale_date(details.getSale_date())
                .sales_header_id(details.getSales_header_id())
                .product_id(details.getProduct_id())
                .price(details.getPrice())
                .quantity(details.getQuantity())
                .build();
    }

}
