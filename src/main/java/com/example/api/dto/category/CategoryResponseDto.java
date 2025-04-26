package com.example.api.dto.category;

import com.example.api.domain.entities.Products;
import com.example.api.dto.product.ProductDetailsDto;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDto {
    private Long id;
    private String nome;
    private List<ProductDetailsDto> produtos;
}
