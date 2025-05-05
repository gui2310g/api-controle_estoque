package com.example.api.domain.entities;

import com.example.api.domain.enums.PurchaseStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity(name = "purchase_orders")
@Data
public class PurchaseOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Suppliers fornecedores;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseStatus status = PurchaseStatus.PENDENTE;

    private Date data;

    @OneToMany(mappedBy = "pedido")
    private List<OrderItems> itens;
}
