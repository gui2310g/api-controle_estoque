package com.example.api.domain.services;

import com.example.api.domain.entities.Users;
import com.example.api.domain.enums.UserRole;
import com.example.api.domain.exceptions.ResourceBadRequestException;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.mappers.UserMapper;
import com.example.api.domain.repositories.UserRepository;
import com.example.api.dto.user.UserRequestDto;
import com.example.api.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserResponseDto create(UserRequestDto dto) {
        if(userRepository.findByEmail(dto.getEmail()).isPresent())
            throw new ResourceBadRequestException("Email ja existente");

        Users usuario = userMapper.toEntity(dto);
        usuario.setDataCriacao(new Date());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        return userMapper.toDto(userRepository.save(usuario));
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(UserRole.USUARIO)).map(userMapper::toDto).toList();
    }

    public UserResponseDto findById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar usuario com id " + id));
    }

    public UserResponseDto update(Long id, UserRequestDto dto) {
        findById(id);
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user.getRole().equals(UserRole.ADMINISTRADOR))
            throw new ResourceBadRequestException("O admin não pode atualizar");

        user.setId(id);
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(passwordEncoder.encode(dto.getSenha()));

        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possivel achar usuario com id " + id));

        if(user.getRole().equals(UserRole.ADMINISTRADOR))
            throw new ResourceBadRequestException("O admin não pode se apagar");

        userRepository.deleteById(id);
    }
}
