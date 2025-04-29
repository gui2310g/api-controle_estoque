package com.example.api.common.events;

import com.example.api.domain.entities.Products;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.repositories.ProductRepository;
import com.example.api.dto.movements.MovementsResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovementEventListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            MovementsResponseDto event = objectMapper.readValue(message.getBody(), MovementsResponseDto.class);
            log.info("Evento de movimentação recebido: {}", event);

            Products product = productRepository.findById(event.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado ao processar evento"));

            if (product.getEstoque_atual() <= product.getEstoque_minimo()) {
                log.warn("Atenção: Produto {} (ID {}) está com estoque baixo: {} unidades",
                        product.getNome(), product.getId(), product.getEstoque_atual());
            }

        } catch (Exception e) {
            log.error("Erro ao processar evento de movimentação", e);
        }
    }
}
