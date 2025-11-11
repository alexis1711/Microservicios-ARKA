package com.arka.sales.service.impl;

import com.arka.sales.dto.SalesDetailsDto;
import com.arka.sales.entity.SalesDetails;
import com.arka.sales.entity.SalesHeaders;
import com.arka.sales.mapper.SalesMapper;
import com.arka.sales.repository.SalesDetailsRepository;
import com.arka.sales.service.SalesDetailsService;
import com.arka.sales.service.SalesHeadersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SalesDetailsServiceImpl implements SalesDetailsService {

    private final SalesDetailsRepository repository;
    private final SalesMapper mapper;

    @Override
    public Mono<SalesDetailsDto> findById(Long id) {
        return repository.findById(id).map(mapper::detailsToDTO);
    }

    @Override
    public Mono<SalesDetailsDto> save(SalesDetailsDto salesDetailsDto) {
        SalesDetails headers = mapper.detailsToEntity(salesDetailsDto);
        return repository.save(headers).map(mapper::detailsToDTO);
    }

    @Override
    public Mono<SalesDetailsDto> update(Long id, SalesDetailsDto salesDetailsDto) {
        return repository.findById(id).flatMap(existing -> {
            existing.setSale_date(salesDetailsDto.getSale_date());
            existing.setSales_header_id(salesDetailsDto.getSales_header_id());
            existing.setProduct_id(salesDetailsDto.getProduct_id());
            existing.setPrice(salesDetailsDto.getPrice());
            existing.setQuantity(salesDetailsDto.getQuantity());
            return repository.save(existing);
        }).map(mapper::detailsToDTO);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Flux<SalesDetailsDto> getDetailsByHeader(Long header_id) {
        return repository.findBySale_Header_Id(header_id).map(mapper::detailsToDTO);
    }
}
