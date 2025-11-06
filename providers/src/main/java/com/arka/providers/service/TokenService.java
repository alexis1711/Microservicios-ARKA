package com.arka.providers.service;

import reactor.core.publisher.Mono;

public interface TokenService {
    Mono<Boolean> isTokenValid(String token);
}
