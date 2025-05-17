package com.example.api.domain.services;

import com.example.api.common.components.PaginationType;
import com.example.api.domain.entities.PurchaseOrders;
import com.example.api.domain.entities.Suppliers;
import com.example.api.domain.enums.PurchaseStatus;
import com.example.api.domain.exceptions.ResourceBadRequestException;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.mappers.PurchaseOrderMapper;
import com.example.api.domain.repositories.PurchaseOrdersRepository;
import com.example.api.domain.repositories.SupplierRepository;
import com.example.api.dto.OrderItems.PurchaseOrdersRequestDto;
import com.example.api.dto.OrderItems.PurchaseOrdersResponseDto;
import com.example.api.dto.OrderItems.PurchaseOrdersUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrdersService {

    private final PurchaseOrdersRepository purchaseOrdersRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PaginationType<PurchaseOrders, PurchaseOrdersResponseDto> paginationType;
    private final SupplierRepository supplierRepository;

    public PurchaseOrdersResponseDto create(PurchaseOrdersRequestDto dto) {
        Suppliers fornecedor = supplierRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));

        PurchaseOrders pedido = purchaseOrderMapper.toEntity(dto);
        pedido.setFornecedores(fornecedor);
        pedido.setData(new Date());
        return purchaseOrderMapper.toDto(purchaseOrdersRepository.save(pedido));
    }

    public List<PurchaseOrdersResponseDto> findAll() {
        return purchaseOrdersRepository.findAll().stream().map(purchaseOrderMapper::toDto).toList();
    }

    public Page<PurchaseOrdersResponseDto> findAllByPage(int page, int size) {
        Page<PurchaseOrders> purchaseOrders = purchaseOrdersRepository.findAll(PageRequest.of(page, size));
        return paginationType.findAll(purchaseOrders, page, size, purchaseOrderMapper::toDto);
    }

    public List<PurchaseOrdersResponseDto> findAllByUserLogged(Long userId) {
        return purchaseOrdersRepository.findById(userId).stream().map(purchaseOrderMapper::toDto).toList();
    }

    public PurchaseOrdersResponseDto findById(Long id) {
        return purchaseOrdersRepository.findById(id).map(purchaseOrderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }

    public PurchaseOrdersResponseDto update(Long id, PurchaseOrdersUpdateDto dto) {
        PurchaseOrders pedido = purchaseOrdersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        pedido.setStatus(dto.getStatus());

        if (pedido.getStatus().equals(PurchaseStatus.APROVADO) || pedido.getStatus().equals(PurchaseStatus.REPROVADO))
            throw new ResourceBadRequestException("Nao se pode atualizar quando o status é APROVADO ou REPROVADO");

        return purchaseOrderMapper.toDto(purchaseOrdersRepository.save(pedido));
    }

    public void delete(Long id) {
        PurchaseOrders pedido = purchaseOrdersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (!pedido.getItens().isEmpty()) throw new ResourceBadRequestException("Nao se pode apagar pedido com itens");

        purchaseOrdersRepository.deleteById(id);
    }
}
