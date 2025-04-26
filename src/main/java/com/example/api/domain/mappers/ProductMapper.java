package com.example.api.domain.mappers;

import com.example.api.domain.entities.Products;
import com.example.api.dto.product.ProductRequestDto;
import com.example.api.dto.product.ProductResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toDto(Products product);
    Products toEntity(ProductRequestDto productRequestDto);
}
