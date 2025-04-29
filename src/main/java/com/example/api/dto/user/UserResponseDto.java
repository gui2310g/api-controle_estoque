package com.example.api.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponseDto {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String role;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date dataCriacao;
}
