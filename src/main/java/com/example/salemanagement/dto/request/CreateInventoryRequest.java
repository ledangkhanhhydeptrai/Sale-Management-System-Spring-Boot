package com.example.salemanagement.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateInventoryRequest {
    private Long warehouseId;
    private Long productId;
    private Integer quantity;
}
