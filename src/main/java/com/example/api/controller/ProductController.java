package com.example.api.controller;

import com.example.api.common.components.AuthHelper;
import com.example.api.domain.services.ProductService;
import com.example.api.dto.product.ProductRequestDto;
import com.example.api.dto.product.ProductResponseDto;
import com.example.api.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final AuthHelper authHelper;

    @PostMapping
    @Operation(summary = "somente o usuario pode criar o produto")
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(description = "o usuario somente os seus proprios, admin pode atualizar os produtos de outros usuarios")
    public ResponseEntity<ProductResponseDto> update(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productService.update(id, dto, authHelper.getAuthenticatedUserId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "somente o admin pode deletar o produto")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductResponseDto>> findAllByPage(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(productService.findAllByPage(page, size));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
