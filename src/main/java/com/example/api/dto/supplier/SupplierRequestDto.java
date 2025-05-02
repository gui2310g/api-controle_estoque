package com.example.api.dto.supplier;

import com.example.api.domain.enums.SuppliersType;
import lombok.Data;

@Data
public class SupplierRequestDto {
    private String nome;
    private SuppliersType tipo;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}