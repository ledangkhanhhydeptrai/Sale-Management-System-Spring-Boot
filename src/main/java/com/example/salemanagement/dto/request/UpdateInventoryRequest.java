package com.example.salemanagement.dto.request;

import com.example.salemanagement.Enum.InventoryStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateInventoryRequest {
    private Integer quantity;
    private InventoryStatus status;
}
