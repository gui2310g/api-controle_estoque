package com.example.api.controller;

import com.example.api.domain.services.MovementsService;
import com.example.api.dto.movements.MovementsRequestDto;
import com.example.api.dto.movements.MovementsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movements")
@AllArgsConstructor
public class MovementsController {

    public MovementsService movementsService;

    @PostMapping
    @Operation(summary = "somente o usuario pode criar o movimento", description =
            "O movimento pode ser de entrada ou saída e ira alterar o estoque do produto, " +
                    "se o estoque for abaixo do minimo ira disparar um alerta")
    public ResponseEntity<MovementsResponseDto> create(@RequestBody MovementsRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementsService.create(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "somente o admin pode retornar o movimento por id")
    public ResponseEntity<MovementsResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movementsService.findById(id));
    }

    @GetMapping
    @Operation(summary = "somente o admin pode retornar o movimento por id")
    public ResponseEntity<List<MovementsResponseDto>> findAll() {
        return ResponseEntity.ok(movementsService.findAll());
    }
}
