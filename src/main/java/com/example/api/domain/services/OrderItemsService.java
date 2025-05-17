package com.example.api.domain.services;

import com.example.api.common.components.PaginationType;
import com.example.api.domain.entities.OrderItems;
import com.example.api.domain.entities.Products;
import com.example.api.domain.entities.PurchaseOrders;
import com.example.api.domain.exceptions.ResourceBadRequestException;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import com.example.api.domain.mappers.OrderItemsMapper;
import com.example.api.domain.repositories.OrderItemsRepository;
import com.example.api.domain.repositories.ProductRepository;
import com.example.api.domain.repositories.PurchaseOrdersRepository;
import com.example.api.dto.PurchaseOrders.OrderItemsRequestDto;
import com.example.api.dto.PurchaseOrders.OrderItemsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;
    private final PurchaseOrdersRepository purchaseOrdersRepository;
    private final PaginationType<OrderItems, OrderItemsResponseDto> paginationType;
    private final OrderItemsMapper orderItemsMapper;
    private final ProductRepository productRepository;

    public OrderItemsResponseDto create(OrderItemsRequestDto dto) {
        Products products = productRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        PurchaseOrders pedido = purchaseOrdersRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));


        OrderItems item = orderItemsMapper.toEntity(dto);
        item.setProduto(products);
        item.setPedido(pedido);
        return orderItemsMapper.toDto(orderItemsRepository.save(item));
    }

    public List<OrderItemsResponseDto> findAll() {
        return orderItemsRepository.findAll().stream().map(orderItemsMapper::toDto).toList();
    }

    public List<OrderItemsResponseDto> findAllByUserLogged(Long userId) {
        return orderItemsRepository.findById(userId).stream().map(orderItemsMapper::toDto).toList();
    }

    public Page<OrderItemsResponseDto> findAllByPage(int page, int size) {
        Page<OrderItems> itens = orderItemsRepository.findAll(PageRequest.of(page, size));
        return paginationType.findAll(itens, page, size, orderItemsMapper::toDto);
    }

    public OrderItemsResponseDto findById(Long id) {
        return orderItemsRepository.findById(id).map(orderItemsMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }

    public OrderItemsResponseDto update(Long id, OrderItemsRequestDto dto) {
        findById(id);
        OrderItems item = orderItemsMapper.toEntity(dto);
        item.setId(id);
        return orderItemsMapper.toDto(orderItemsRepository.save(item));
    }
    public void delete(Long id) {
        PurchaseOrders pedido = purchaseOrdersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (!pedido.getItens().isEmpty()) throw new ResourceBadRequestException("Nao se pode apagar pedido com itens");

        purchaseOrdersRepository.deleteById(id);
    }
}
