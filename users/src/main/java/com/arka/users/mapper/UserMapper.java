package com.arka.users.mapper;

import com.arka.users.dto.UserDto;
import com.arka.users.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }

    public UserDto toDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }
}
