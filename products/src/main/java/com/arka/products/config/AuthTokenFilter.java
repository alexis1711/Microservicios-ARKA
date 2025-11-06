package com.arka.products.config;

import lombok.RequiredArgsConstructor;
import com.arka.products.repository.TokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter implements WebFilter {

    private final TokenRepository tokenRepository;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String tokenValue = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization"); // Espera "Bearer <token>"

        if (tokenValue == null || !tokenValue.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        tokenValue = tokenValue.substring(7); // Quitar "Bearer "

        return tokenRepository.findByToken(tokenValue)
                .flatMap(token -> {
                    LocalDateTime limite = LocalDateTime.now().minusHours(24);
                    if (token.getCreatedAt().isAfter(limite)) {
                        return chain.filter(exchange); // Token válido, continuar
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                })
                .switchIfEmpty(Mono.defer(() -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }));
    }
}
