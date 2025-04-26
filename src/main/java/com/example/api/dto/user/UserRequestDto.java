package com.example.api.dto.user;

import lombok.Data;

@Data
public class UserRequestDto {
    private String nome;
    private String email;
    private String senha;
}
