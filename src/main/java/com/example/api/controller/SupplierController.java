package com.example.api.controller;

import com.example.api.domain.services.SupplierService;
import com.example.api.dto.supplier.SupplierRequestDto;
import com.example.api.dto.supplier.SupplierResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    @Operation(summary = "somente o usuario pode criar o fornecedor", description = "pode ser fisica ou juridica")
    public ResponseEntity<SupplierResponseDto> create(@RequestBody SupplierRequestDto dto) {
        return ResponseEntity.ok(supplierService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponseDto>> findAll() {
        return ResponseEntity.ok(supplierService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "somente o usuario pode atualizar o fornecedor")
    public ResponseEntity<SupplierResponseDto> update(@PathVariable Long id, @RequestBody SupplierRequestDto dto) {
        return ResponseEntity.ok(supplierService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "somente o admin pode deletar o fornecedor")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}