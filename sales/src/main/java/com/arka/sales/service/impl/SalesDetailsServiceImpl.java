package com.arka.sales.service.impl;

import com.arka.sales.dto.SalesDetailsDto;
import com.arka.sales.entity.SalesDetails;
import com.arka.sales.mapper.SalesMapper;
import com.arka.sales.repository.SalesDetailsRepository;
import com.arka.sales.service.SalesDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesDetailsServiceImpl implements SalesDetailsService {
    private final SalesDetailsRepository repository;
    private final SalesMapper mapper;

    @Override
    public SalesDetailsDto findById(Long id) {
        return mapper.detailsToDTO(repository.findById(id).orElse(new SalesDetails()));
    }

    @Override
    public SalesDetailsDto save(SalesDetailsDto salesDetailsDto) {
        SalesDetails headers = mapper.detailsToEntity(salesDetailsDto);
        return mapper.detailsToDTO(repository.save(headers));
    }

    @Override
    public SalesDetailsDto update(Long id, SalesDetailsDto salesDetailsDto) {
        SalesDetails existing = repository.findById(id).get();
        existing.setQuantity(salesDetailsDto.getQuantity());
        return mapper.detailsToDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<SalesDetailsDto> getDetailsByHeader(Long header_id) {
        List<SalesDetailsDto> salesDetailsDtos = new ArrayList<>();
        for(SalesDetails salesDetails : repository.findBySalesHeaderId(header_id)){
            SalesDetailsDto dto = mapper.detailsToDTO(salesDetails);
            salesDetailsDtos.add(dto);
        }
        return salesDetailsDtos;
    }
}
