package com.example.api.domain.services;

import com.example.api.domain.entities.Suppliers;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.mappers.SupplierMapper;
import com.example.api.domain.repositories.SupplierRepository;
import com.example.api.dto.supplier.SupplierRequestDto;
import com.example.api.dto.supplier.SupplierResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierResponseDto create(SupplierRequestDto dto) {
        Suppliers entity = supplierMapper.toEntity(dto);
        return supplierMapper.toDto(supplierRepository.save(entity));
    }

    public List<SupplierResponseDto> findAll() {
        return supplierRepository.findAll().stream().map(supplierMapper::toDto).toList();
    }

    public SupplierResponseDto findById(Long id) {
        return supplierRepository.findById(id).map(supplierMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado da id " + id));
    }

    public SupplierResponseDto update(Long id, SupplierRequestDto dto) {
        findById(id);
        Suppliers supplier = supplierMapper.toEntity(dto);
        supplier.setId(id);

        return supplierMapper.toDto(supplierRepository.save(supplier));
    }

    public void delete(Long id) {
        findById(id);
        supplierRepository.deleteById(id);
    }
}