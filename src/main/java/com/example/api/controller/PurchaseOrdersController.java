package com.example.api.controller;

import com.example.api.domain.services.PurchaseOrdersService;
import com.example.api.dto.OrderItems.PurchaseOrdersRequestDto;
import com.example.api.dto.OrderItems.PurchaseOrdersResponseDto;
import com.example.api.dto.OrderItems.PurchaseOrdersUpdateDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-orders")
@AllArgsConstructor
public class PurchaseOrdersController {

    private final PurchaseOrdersService purchaseOrdersService;

    @PostMapping
    public ResponseEntity<PurchaseOrdersResponseDto> create (@RequestBody PurchaseOrdersRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrdersService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrdersResponseDto>> findAll() {
        return ResponseEntity.ok(purchaseOrdersService.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PurchaseOrdersResponseDto>> findAllByPage(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(purchaseOrdersService.findAllByPage(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrdersResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrdersService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrdersResponseDto> update(
            @PathVariable Long id,
            @RequestBody PurchaseOrdersUpdateDto dto
    ) {
        return ResponseEntity.ok(purchaseOrdersService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        purchaseOrdersService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
