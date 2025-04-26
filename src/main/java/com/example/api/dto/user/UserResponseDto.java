package com.example.api.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponseDto {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String role;
    private Date dataCriacao;
}
