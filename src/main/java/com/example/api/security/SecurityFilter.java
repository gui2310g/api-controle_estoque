package com.example.api.security;

import com.example.api.domain.entities.Users;
import com.example.api.domain.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var token = recoverToken(request);

        if(token != null) {
            var login = tokenService.validateToken(token);
            Optional<Users> userOpt = userRepository.findByEmail(login);

            userOpt.ifPresent(user -> SecurityContextHolder.getContext().setAuthentication(getAuthentication(user)));
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var header = request.getHeader("Authorization");

        return header != null ? header.replace("Bearer ", "") : null;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(Users user) {
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
