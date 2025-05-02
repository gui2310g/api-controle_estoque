package com.example.api.domain.entities;

import com.example.api.domain.enums.SuppliersType;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "suppliers")
@Data
public class Suppliers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SuppliersType tipo;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String numero;

    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    private String cep;


}
