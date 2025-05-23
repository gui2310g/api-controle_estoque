package com.example.api.controller;

import com.example.api.dto.login.LoginRequestDto;
import com.example.api.dto.login.LoginResponseDto;
import com.example.api.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated LoginRequestDto dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken(dto.getEmail(), dto.getSenha());

        var role = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .findFirst()
                .orElse("ROLE_USER");

        return ResponseEntity.ok(new LoginResponseDto(token, role));
    }
}
