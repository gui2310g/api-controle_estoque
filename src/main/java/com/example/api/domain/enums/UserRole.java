package com.example.api.domain.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMINISTRADOR("ADMINISTRADOR"),
    USUARIO("USUARIO");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
