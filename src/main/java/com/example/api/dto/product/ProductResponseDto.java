package com.example.api.dto.product;

import com.example.api.dto.category.CategoryDetailsDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;

    private String nome;

    private String descricao;

    private int preco;

    private int estoque_atual;

    private int estoque_minimo;

    private CategoryDetailsDto categoria;

    private String sku;
}
