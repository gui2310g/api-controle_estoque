package com.example.api.domain.entities;

import com.example.api.domain.enums.MovementsType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "movements")
@Data
public class Movements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Products produtos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementsType tipo;

    private int quantidade;

    private Date data;
}
