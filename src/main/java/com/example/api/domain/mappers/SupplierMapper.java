package com.example.api.domain.mappers;

import com.example.api.domain.entities.Suppliers;
import com.example.api.dto.supplier.SupplierRequestDto;
import com.example.api.dto.supplier.SupplierResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierResponseDto toDto(Suppliers supplier);
    Suppliers toEntity(SupplierRequestDto dto);
}
