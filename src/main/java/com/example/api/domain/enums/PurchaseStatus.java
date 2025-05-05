package com.example.api.domain.enums;

import lombok.Getter;

@Getter
public enum PurchaseStatus {
    PENDENTE("PENDENTE"),
    APROVADO("APROVADO"),
    REPROVADO("REPROVADO");

    private final String tipo;

    PurchaseStatus(String tipo) {
        this.tipo = tipo;
    }
}
