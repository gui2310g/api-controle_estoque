package com.example.api.domain.mappers;

import com.example.api.domain.entities.OrderItems;
import com.example.api.dto.OrderItems.OrderItemsRequestDto;
import com.example.api.dto.OrderItems.OrderItemsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

   @Mapping(source = "pedido.id", target = "pedidoId")
   @Mapping(source = "produto.id", target = "produtoId")
   @Mapping(source = "usuario.id", target = "userId")
   OrderItemsResponseDto toDto(OrderItems orderItems);

   OrderItems toEntity(OrderItemsRequestDto orderItemsRequestDto);

}
