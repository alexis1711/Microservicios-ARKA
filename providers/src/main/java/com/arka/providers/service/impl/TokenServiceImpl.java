package com.arka.providers.service.impl;

import com.arka.providers.repository.TokenRepository;
import com.arka.providers.service.TokenService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Mono<Boolean> isTokenValid(String token) {
        return tokenRepository.findByToken(token)
                .map(tokenEntity -> {
                    // Token válido si fue creado hace menos de 24 horas
                    LocalDateTime now = LocalDateTime.now();
                    return Duration.between(tokenEntity.getCreatedAt(), now).toHours() < 24;
                })
                .defaultIfEmpty(false); // Si no existe el token → inválido
    }
}
