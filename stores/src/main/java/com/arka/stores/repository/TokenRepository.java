package com.arka.stores.repository;

import com.arka.stores.entity.Token;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TokenRepository extends ReactiveCrudRepository<Token, Long> {
    Mono<Token> findByToken(String token);
}
