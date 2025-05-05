package com.example.api.dto.PurchaseOrders;

import lombok.Data;

@Data
public class OrderItemsResponseDto {
    private Long id;
    private Long pedidoId;
    private Long produtoId;
    private int quantidade = 1;
    private int preco;
}
