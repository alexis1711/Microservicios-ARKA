package com.arka.users.service.impl;

import com.arka.users.dto.LoginRequestDto;
import com.arka.users.dto.LoginResponseDto;
import com.arka.users.dto.UserDto;
import com.arka.users.entity.Token;
import com.arka.users.mapper.UserMapper;
import com.arka.users.repository.TokenRepository;
import com.arka.users.repository.UserRepository;
import com.arka.users.service.UserService;
import com.arka.users.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserMapper mapper;
    private final JwtUtil jwtUtil;

    @Override
    public Flux<UserDto> findAll() {
        return userRepository.findAll().map(mapper::toDto);
    }

    @Override
    public Mono<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")));
    }

    @Override
    public Mono<UserDto> save(UserDto userDto) {
        return userRepository.save(mapper.toEntity(userDto)).map(mapper::toDto);
    }

    @Override
    public Mono<UserDto> update(Long id, UserDto userDto) {
        return userRepository.findById(id)
                .flatMap(existing -> {
                    existing.setUsername(userDto.getUsername());
                    existing.setPassword(userDto.getPassword());
                    return userRepository.save(existing);
                })
                .map(mapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return null;
    }

    @Override
    public Mono<LoginResponseDto> login(LoginRequestDto loginRequest) {
        return userRepository.findByUsername(loginRequest.getUsername())
                .flatMap(user -> {
                    if (!user.getPassword().equals(loginRequest.getPassword())) {
                        return Mono.error(new RuntimeException("Contraseña incorrecta"));
                    }

                    String tokenValue = jwtUtil.generateToken(user.getUsername());
                    Token token = Token.builder()
                            .userId(user.getId())
                            .token(tokenValue)
                            .createdAt(LocalDateTime.now())
                            .build();

                    return tokenRepository.save(token)
                            .thenReturn(LoginResponseDto.builder()
                                    .token(tokenValue)
                                    .message("Login exitoso")
                                    .build());
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")));
    }
}
