package com.example.api.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetailsDto {
    private Long id;

    private String nome;

    private String descricao;

    private int preco;

    private int estoque_atual;

    private int estoque_minimo;
    
    private String sku;
}
