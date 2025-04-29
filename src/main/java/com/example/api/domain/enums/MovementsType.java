package com.example.api.domain.enums;

import lombok.Getter;

@Getter
public enum MovementsType {
    ENTRADA("ENTRADA"),
    SAIDA("SAIDA");

    private final String type;

    MovementsType(String type) {
        this.type = type;
    }
}
