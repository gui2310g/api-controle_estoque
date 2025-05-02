package com.example.api.domain.enums;

import lombok.Getter;

@Getter
public enum SuppliersType {
    FISICA("FISICA"),
    JURIDICA("JURIDICA");

    private final String type;

    SuppliersType(String type) {
        this.type = type;
    }
}
