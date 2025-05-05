package com.example.api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "order_items")
@Data
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private PurchaseOrders pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Products produto;

    private int quantidade;

    @Column(nullable = false)
    private int preco;
}
