package com.example.api.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private String dataHour;

    private Integer status;

    private String title;

    private String message;
}

