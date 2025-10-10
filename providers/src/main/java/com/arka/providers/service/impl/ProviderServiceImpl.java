package com.arka.providers.service.impl;

import com.arka.providers.dto.ProviderDto;
import com.arka.providers.entity.Provider;
import com.arka.providers.mapper.ProviderMapper;
import com.arka.providers.repository.ProviderRepository;
import com.arka.providers.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final ProviderMapper mapper;

    @Override
    public Flux<ProviderDto> findAll() {
        return providerRepository.findAll().map(mapper::toDto);
    }

    @Override
    public Mono<ProviderDto> findById(Long id) {
        return providerRepository.findById(id).map(mapper::toDto);
    }

    @Override
    public Mono<ProviderDto> save(ProviderDto providerDto) {
        Provider provider = mapper.toEntity(providerDto);
        return providerRepository.save(provider).map(mapper::toDto);
    }

    @Override
    public Mono<ProviderDto> update(Long id, ProviderDto providerDto) {
        return providerRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(providerDto.getName());
                    existing.setEmail(providerDto.getEmail());
                    existing.setTelephone(providerDto.getTelephone());
                    existing.setCountry(providerDto.getCountry());
                    existing.setCategory(providerDto.getCategory());
                    return providerRepository.save(existing);
                })
                .map(mapper::toDto);
    }

    @Override
    public Mono<Void> inactive(Long id) {
        return null;
    }
}
