package com.example.api.dto.PurchaseOrders;

import lombok.Data;

@Data
public class OrderItemsRequestDto {
    private Long pedidoId;
    private Long produtoId;
    private int quantidade = 1;
    private int preco;
}
