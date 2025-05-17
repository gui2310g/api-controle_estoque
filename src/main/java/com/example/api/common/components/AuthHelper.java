package com.example.api.common.components;

import com.example.api.domain.entities.Users;
import com.example.api.domain.enums.UserRole;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthHelper {

    private final UserRepository userRepository;

    public Users getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public Long getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }

    public boolean isAdmin() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        Users users = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return users.getRole() == UserRole.ADMINISTRADOR;
    }
}
