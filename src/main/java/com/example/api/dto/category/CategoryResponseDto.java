package com.example.api.dto.category;

import com.example.api.dto.product.ProductDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String nome;
    private List<ProductDetailsDto> produtos;
}
