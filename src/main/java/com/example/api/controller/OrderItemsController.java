package com.example.api.controller;

import com.example.api.common.components.AuthHelper;
import com.example.api.domain.services.OrderItemsService;
import com.example.api.dto.OrderItems.OrderItemsRequestDto;
import com.example.api.dto.OrderItems.OrderItemsResponseDto;
import com.example.api.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@AllArgsConstructor
public class OrderItemsController {

    private final OrderItemsService orderItemsService;

    private final AuthHelper authHelper;


    @GetMapping
    public ResponseEntity<List<OrderItemsResponseDto>> findAll() {
         return ResponseEntity.ok(orderItemsService.findAll());
    }

    @PostMapping
    public OrderItemsResponseDto create(@RequestBody OrderItemsRequestDto orderItem) {
        return orderItemsService.create(orderItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemsResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemsService.findById(id));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<OrderItemsResponseDto>> findAllByPage(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(orderItemsService.findAllByPage(page, size));
    }

    @GetMapping("/findByAuth")
    public ResponseEntity<List<OrderItemsResponseDto>> findAllByUserLogged() {
        return ResponseEntity.ok(orderItemsService.findAllByUserLogged(authHelper.getAuthenticatedUserId()));
    }

    @PutMapping("/{id}")
    @Operation(description = "o usuario somente os seus proprios, admin pode atualizar os itens de outros usuarios")
    public ResponseEntity<OrderItemsResponseDto> update(@PathVariable Long id, @RequestBody OrderItemsRequestDto dto) {
        return ResponseEntity.ok(orderItemsService.update(id, dto, authHelper.getAuthenticatedUserId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "somente o admin pode deletar o item do pedido")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderItemsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
