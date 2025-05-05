package com.example.api.dto.OrderItems;

import com.example.api.domain.enums.PurchaseStatus;
import lombok.Data;

@Data
public class PurchaseOrdersUpdateDto {
    private PurchaseStatus status;
}
