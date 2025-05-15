package com.example.api.controller;

import com.example.api.domain.entities.Alert;
import com.example.api.domain.services.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@AllArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public ResponseEntity<List<Alert>> findAll() {
        return ResponseEntity.ok(alertService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alert> findById(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "somente o admin pode deletar o Id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
