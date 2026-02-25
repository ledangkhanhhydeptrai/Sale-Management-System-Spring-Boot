package com.example.salemanagement.dto.response;

import com.example.salemanagement.Enum.InventoryStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryResponse {
    private Long id;
    private Integer quantity;
    private InventoryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long productId;
    private Long wareHouseId;
}
