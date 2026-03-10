package com.example.salemanagement.dto.response;

import com.example.salemanagement.Enum.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private Long storeId;
    private String name;
    private BigDecimal price;
    private String images;
    private ProductStatus status;
    private LocalDateTime createdAt;
}
