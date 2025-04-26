package com.example.api.domain.mappers;

import com.example.api.domain.entities.Users;
import com.example.api.dto.user.UserRequestDto;
import com.example.api.dto.user.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(Users users);

    Users toEntity(UserRequestDto userRequestDto);
}
