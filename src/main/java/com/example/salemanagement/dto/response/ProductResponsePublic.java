package com.example.salemanagement.dto.response;

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
    private String images;
    private LocalDateTime createdAt;
}
