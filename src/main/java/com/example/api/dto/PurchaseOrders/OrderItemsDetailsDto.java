package com.example.api.dto.PurchaseOrders;

import lombok.Data;

@Data
public class OrderItemsDetailsDto {
    private Long id;
    private int quantidade;
    private int preco;
}
