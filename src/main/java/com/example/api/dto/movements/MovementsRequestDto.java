package com.example.api.dto.movements;

import com.example.api.domain.enums.MovementsType;
import lombok.Data;

@Data
public class MovementsRequestDto {
    private Long produtoId;
    private MovementsType tipo;
    private int quantidade = 1;
}
