package com.example.api.dto.PurchaseOrders;

import com.example.api.domain.enums.PurchaseStatus;
import com.example.api.dto.OrderItems.OrderItemsDetailsDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PurchaseOrdersResponseDto {
    private Long id;
    private Long userId;
    private Long fornecedorId;
    private PurchaseStatus status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date data;

    private List<OrderItemsDetailsDto> itens;
}
