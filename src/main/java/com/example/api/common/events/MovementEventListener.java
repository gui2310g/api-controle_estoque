package com.example.api.common.events;

import com.example.api.domain.entities.Alert;
import com.example.api.domain.entities.Products;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.repositories.AlertRepository;
import com.example.api.domain.repositories.ProductRepository;
import com.example.api.dto.movements.MovementsResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovementEventListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final AlertRepository alertRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            MovementsResponseDto event = objectMapper.readValue(message.getBody(), MovementsResponseDto.class);
            log.info("Evento de movimentação recebido: {}", event);

            Products product = productRepository.findById(event.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado ao processar evento"));

            if (product.getEstoque_atual() <= product.getEstoque_minimo()) {
                log.warn("Estoque baixo detectado para o produto {} (ID {})", product.getNome(), product.getId());

                Alert alert = new Alert();
                alert.setProdutoId(product.getId());
                alert.setMensagem(
                        "Estoque baixo: " + product.getNome() + " com " + product.getEstoque_atual() + " unidades."
                );
                alert.setDataCriacao(new Date());

                alertRepository.save(alert);

                log.info("Alerta registrado para o produto {} ", product.getNome());
            }

        } catch (Exception e) {
            log.error("Erro ao processar evento de movimentação", e);
        }
    }
}
