package com.example.api.controller;

import com.example.api.common.components.AuthHelper;
import com.example.api.domain.services.PurchaseOrdersService;
import com.example.api.dto.PurchaseOrders.PurchaseOrdersRequestDto;
import com.example.api.dto.PurchaseOrders.PurchaseOrdersResponseDto;
import com.example.api.dto.PurchaseOrders.PurchaseOrdersUpdateDto;
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
@RequestMapping("/purchase-orders")
@AllArgsConstructor
public class PurchaseOrdersController {

    private final PurchaseOrdersService purchaseOrdersService;

    private final AuthHelper authHelper;

    @PostMapping
    @Operation(summary = "somente user pode criar pedido", description = "status padrão é PENDENTE")
    public ResponseEntity<PurchaseOrdersResponseDto> create(@RequestBody PurchaseOrdersRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrdersService.create(dto));
    }

    @GetMapping
    @Operation(summary = "somente o usuario pode retornar os pedidos")
    public ResponseEntity<List<PurchaseOrdersResponseDto>> findAll() {
        return ResponseEntity.ok(purchaseOrdersService.findAll());
    }

    @GetMapping("/findByAuth")
    public ResponseEntity<List<PurchaseOrdersResponseDto>> findAllByUserLogged() {
        return ResponseEntity.ok(purchaseOrdersService.findAllByUserLogged(authHelper.getAuthenticatedUserId()));
    }

    @GetMapping("/page")
    @Operation(summary = "somente o usuario pode retornar os pedidos por paginaçao")
    public ResponseEntity<Page<PurchaseOrdersResponseDto>> findAllByPage(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(purchaseOrdersService.findAllByPage(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "somente o usuario pode retornar o pedido por id")
    public ResponseEntity<PurchaseOrdersResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrdersService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "atualiza o pedido de acordo com o status", description =
            "o usuario pode atualizar se estiver como pendente, o admin pode atualizar para aprovado ou reprovado"
    )
    public ResponseEntity<PurchaseOrdersResponseDto> update(
            @PathVariable Long id,
            @RequestBody PurchaseOrdersUpdateDto dto
    ) {
        return ResponseEntity.ok(purchaseOrdersService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "somente o admin pode deletar o pedido")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        purchaseOrdersService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
