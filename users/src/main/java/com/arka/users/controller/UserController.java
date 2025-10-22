package com.arka.users.controller;

import com.arka.users.dto.LoginRequestDto;
import com.arka.users.dto.LoginResponseDto;
import com.arka.users.dto.UserDto;
import com.arka.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public Flux<UserDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Mono<UserDto> create(@RequestBody UserDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public Mono<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/login")
    public Mono<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        return service.login(loginRequest);
    }
}
