package com.example.api.controller;

import com.example.api.domain.services.SupplierService;
import com.example.api.dto.supplier.SupplierRequestDto;
import com.example.api.dto.supplier.SupplierResponseDto;
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
    public ResponseEntity<SupplierResponseDto> update(@PathVariable Long id, @RequestBody SupplierRequestDto dto) {
        return ResponseEntity.ok(supplierService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}