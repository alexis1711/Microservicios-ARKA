package com.arka.sales.service.impl;

import com.arka.sales.dto.SalesHeadersDto;
import com.arka.sales.entity.SalesDetails;
import com.arka.sales.entity.SalesHeaders;
import com.arka.sales.mapper.SalesMapper;
import com.arka.sales.repository.SalesHeadersRepository;
import com.arka.sales.service.SalesHeadersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SalesHeadersServiceImpl implements SalesHeadersService {

    private final SalesHeadersRepository repository;
    private final SalesMapper mapper;

    @Override
    public Mono<SalesHeadersDto> findById(Long id) {
        return repository.findById(id).map(mapper::headersToDTO);
    }

    @Override
    public Mono<SalesHeadersDto> save(SalesHeadersDto salesHeadersDto) {
        SalesHeaders headers = mapper.headersToEntity(salesHeadersDto);
        return repository.save(headers).map(mapper::headersToDTO);
    }

    @Override
    public Mono<SalesHeadersDto> update(Long id, SalesHeadersDto salesHeadersDto) {
        return repository.findById(id).flatMap(existing -> {
            existing.setSale_date(salesHeadersDto.getSale_date());
            existing.setSale_value(salesHeadersDto.getSale_value());
            existing.setCustomer_id(salesHeadersDto.getCustomer_id());
            existing.setCustomer_email(salesHeadersDto.getCustomer_email());
            existing.setStatus(salesHeadersDto.getStatus());
            return repository.save(existing);
        }).map(mapper::headersToDTO);
    }
}
