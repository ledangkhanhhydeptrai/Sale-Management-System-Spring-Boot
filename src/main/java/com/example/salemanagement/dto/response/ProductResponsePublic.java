package com.example.salemanagement.dto.response;

import com.example.salemanagement.Enum.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
public class ProductResponsePublic {
    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createdAt;
}
