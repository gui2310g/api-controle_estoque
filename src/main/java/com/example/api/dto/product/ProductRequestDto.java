package com.example.api.dto.product;

import com.example.api.domain.entities.Categories;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ProductRequestDto {
    private String nome;

    private String descricao;

    private int preco;

    private int estoque_atual;

    private int estoque_minimo = 1;

    private Long categoriaId;
}
