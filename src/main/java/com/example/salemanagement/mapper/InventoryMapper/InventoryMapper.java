package com.example.salemanagement.mapper.InventoryMapper;

import com.example.salemanagement.dto.response.InventoryResponse;
import com.example.salemanagement.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {
    public InventoryResponse toInventoryResponse(Inventory inventory) {
        if (inventory == null)
            return null;
        return InventoryResponse.builder()
                .id(inventory.getId())
                .quantity(inventory.getQuantity())
                .status(inventory.getStatus())
                .updatedAt(inventory.getUpdatedAt())
                .createdAt(inventory.getCreatedAt())
                .wareHouseId(inventory.getWarehouse().getId())
                .productId(inventory.getProduct().getId())
                .build();
    }
}
