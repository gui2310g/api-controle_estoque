package com.example.api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "categories")
@Data
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "categoria")
    private List<Products> produtos;
}
