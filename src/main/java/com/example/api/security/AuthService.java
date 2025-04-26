package com.example.api.security;

import com.example.api.domain.entities.Users;
import com.example.api.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email não achado"));
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .map(Users::getId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
