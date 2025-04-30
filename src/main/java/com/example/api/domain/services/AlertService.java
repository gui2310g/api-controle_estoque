package com.example.api.domain.services;

import com.example.api.domain.entities.Alert;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.repositories.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    public List<Alert> findAll() {
        return alertRepository.findAll().stream().toList();
    }

    public Alert findById(Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado com id " + id));
    }

    public void delete(Long id) {
        findById(id);
        alertRepository.deleteById(id);
    }
}
