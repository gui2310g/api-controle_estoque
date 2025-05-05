package com.example.api.controller;

import com.example.api.domain.services.OrderItemsService;
import com.example.api.dto.PurchaseOrders.OrderItemsRequestDto;
import com.example.api.dto.PurchaseOrders.OrderItemsResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@AllArgsConstructor
public class OrderItemsController {

    private final OrderItemsService orderItemsService;

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

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemsResponseDto> update(@PathVariable Long id, @RequestBody OrderItemsRequestDto dto) {
        return ResponseEntity.ok(orderItemsService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderItemsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
