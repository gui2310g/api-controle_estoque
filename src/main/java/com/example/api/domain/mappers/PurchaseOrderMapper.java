package com.example.api.domain.mappers;

import com.example.api.domain.entities.PurchaseOrders;
import com.example.api.dto.OrderItems.PurchaseOrdersRequestDto;
import com.example.api.dto.OrderItems.PurchaseOrdersResponseDto;
import com.example.api.dto.OrderItems.PurchaseOrdersUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {

    @Mapping(source = "fornecedores.id", target = "fornecedorId")
    @Mapping(source = "itens", target = "itens")
    PurchaseOrdersResponseDto toDto(PurchaseOrders purchaseOrder);
    PurchaseOrders toEntity(PurchaseOrdersRequestDto purchaseOrdersResquestDto);

    PurchaseOrdersResponseDto toUpdateDto(PurchaseOrdersUpdateDto purchaseOrdersUpdateDto);
}
