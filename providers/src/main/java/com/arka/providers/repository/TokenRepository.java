package com.arka.providers.repository;

import com.arka.providers.entity.Token;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TokenRepository extends ReactiveCrudRepository<Token, Long> {
    Mono<Token> findByToken(String token);
}
