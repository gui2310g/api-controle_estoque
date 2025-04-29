package com.example.api.domain.mappers;

import com.example.api.domain.entities.Movements;
import com.example.api.dto.movements.MovementsRequestDto;
import com.example.api.dto.movements.MovementsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovementsMapper {

    @Mapping(source = "produtos.id", target = "produtoId")
    MovementsResponseDto toDto(Movements movements);

    Movements toEntity(MovementsRequestDto movementsRequestDto);
}
