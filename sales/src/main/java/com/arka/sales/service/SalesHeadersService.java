package com.arka.sales.service;

import com.arka.sales.dto.HeadersDetailsDto;
import com.arka.sales.dto.SalesHeadersDto;

public interface SalesHeadersService {
    SalesHeadersDto findById(Long id);
    SalesHeadersDto save(SalesHeadersDto salesHeadersDto);
    SalesHeadersDto update(Long id, SalesHeadersDto salesHeadersDto);
    HeadersDetailsDto paySale(SalesHeadersDto headersDto);
}
