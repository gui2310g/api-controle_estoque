package com.example.api.controller;

import com.example.api.domain.services.OrderItemsService;
import com.example.api.dto.PurchaseOrders.OrderItemsRequestDto;
import com.example.api.dto.PurchaseOrders.OrderItemsResponseDto;
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

    private final AuthService authService;

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
    public ResponseEntity<List<OrderItemsResponseDto>> findAllByUserLogged(Authentication auth) {
        return ResponseEntity.ok(orderItemsService.findAllByUserLogged(authService.getAuthenticatedUserId(auth)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "somente o usuario pode atualizar o item de pedido")
    public ResponseEntity<OrderItemsResponseDto> update(@PathVariable Long id, @RequestBody OrderItemsRequestDto dto) {
        return ResponseEntity.ok(orderItemsService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "somente o admin pode deletar o item de pedido")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderItemsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
