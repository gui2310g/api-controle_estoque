package com.example.api.dto.OrderItems;

import lombok.Data;

@Data
public class OrderItemsResponseDto {
    private Long id;
    private Long userId;
    private Long pedidoId;
    private Long produtoId;
    private int quantidade = 1;
    private int preco;
}
