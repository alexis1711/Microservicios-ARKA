package com.arka.users.service;

import com.arka.users.dto.LoginRequestDto;
import com.arka.users.dto.LoginResponseDto;
import com.arka.users.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserDto> findAll();
    Mono<UserDto> findById(Long id);
    Mono<UserDto> save(UserDto userDto);
    Mono<UserDto> update(Long id, UserDto userDto);
    Mono<Void> delete(Long id);
    Mono<LoginResponseDto> login(LoginRequestDto loginRequest);
}
