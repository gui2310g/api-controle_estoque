package com.example.api.domain.services;

import com.example.api.common.events.MovementEventPublisher;
import com.example.api.domain.entities.Movements;
import com.example.api.domain.entities.Products;
import com.example.api.domain.enums.MovementsType;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.mappers.MovementsMapper;
import com.example.api.domain.repositories.MovementsRepository;
import com.example.api.domain.repositories.ProductRepository;
import com.example.api.dto.movements.MovementsRequestDto;
import com.example.api.dto.movements.MovementsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementsService {

    private final MovementsRepository movementRepository;
    private final ProductRepository productRepository;
    private final MovementEventPublisher eventPublisher;
    private final MovementsMapper movementsMapper;

    public MovementsResponseDto create(MovementsRequestDto dto) {
        Products product = productRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        Movements movement = movementsMapper.toEntity(dto);

        movement.setProdutos(product);
        movement.setData(new Date());

        if (dto.getTipo() == MovementsType.ENTRADA)
            product.setEstoque_atual(product.getEstoque_atual() + dto.getQuantidade());

        if (dto.getTipo() == MovementsType.SAIDA) {
            if (product.getEstoque_atual() < dto.getQuantidade())
                throw new ResourceNotFoundException("Estoque insuficiente para saída");

            product.setEstoque_atual(product.getEstoque_atual() - dto.getQuantidade());
        }

        if(dto.getQuantidade() == 0) throw new ResourceNotFoundException("Quantidade não pode ser zero");

        Movements savedMovement = movementRepository.save(movement);

        eventPublisher.publishMovementCreated(movementsMapper.toDto(savedMovement));

        return movementsMapper.toDto(savedMovement);
    }

    public MovementsResponseDto findById(Long id) {
        return movementRepository.findById(id).map(movementsMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado movimento da id " + id));
    }

    public List<MovementsResponseDto> findAll() {
        return movementRepository.findAll().stream().map(movementsMapper::toDto).toList();
    }
}
