package com.example.api.dto.movements;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MovementsResponseDto {
    private Long id;
    private Long produtoId;
    private String tipo;
    private int quantidade;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date data;
}
