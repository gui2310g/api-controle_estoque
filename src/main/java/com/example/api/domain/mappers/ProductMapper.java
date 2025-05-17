package com.example.api.domain.mappers;

import com.example.api.domain.entities.Products;
import com.example.api.dto.product.ProductRequestDto;
import com.example.api.dto.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "usuario.id", target = "userId")
    ProductResponseDto toDto(Products product);
    Products toEntity(ProductRequestDto productRequestDto);
}
