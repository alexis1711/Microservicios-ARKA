package com.arka.sales.service;

import com.arka.sales.dto.SalesDetailsDto;

import java.util.List;

public interface SalesDetailsService {
    SalesDetailsDto findById(Long id);
    SalesDetailsDto save(SalesDetailsDto salesDetailsDto);
    SalesDetailsDto update(Long id, SalesDetailsDto salesDetailsDto);
    void delete(Long id);
    List<SalesDetailsDto> getDetailsByHeader(Long header_id);
}
