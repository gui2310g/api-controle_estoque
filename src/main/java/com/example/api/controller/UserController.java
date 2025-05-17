package com.example.api.controller;


import com.example.api.common.components.AuthHelper;
import com.example.api.domain.services.UserService;
import com.example.api.dto.user.UserRequestDto;
import com.example.api.dto.user.UserResponseDto;
import com.example.api.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private AuthHelper authHelper;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @GetMapping
    @Operation(summary = "somente o admin pode retornar os usuarios")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "somente o admin pode retornar o usuario poor id")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping
    @Operation(summary = "somente o usuario pode se atualizar")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.update(authHelper.getAuthenticatedUserId(), request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "somente o admin pode deletar o usuario")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
