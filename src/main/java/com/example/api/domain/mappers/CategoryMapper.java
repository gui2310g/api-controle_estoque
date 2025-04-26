package com.example.api.domain.mappers;

import com.example.api.domain.entities.Categories;
import com.example.api.dto.category.CategoryRequestDto;
import com.example.api.dto.category.CategoryResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDto(Categories categories);

    Categories toEntity(CategoryRequestDto categoryRequestDto);
}
