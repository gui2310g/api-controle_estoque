package com.example.api.controller;

import com.example.api.domain.services.MovementsService;
import com.example.api.dto.movements.MovementsRequestDto;
import com.example.api.dto.movements.MovementsResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movements")
@AllArgsConstructor
public class MovementsController {

    public MovementsService movementsService;

    @PostMapping
    public ResponseEntity<MovementsResponseDto> create(@RequestBody MovementsRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementsService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementsResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movementsService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<MovementsResponseDto>> findAll() {
        return ResponseEntity.ok(movementsService.findAll());
    }
}
