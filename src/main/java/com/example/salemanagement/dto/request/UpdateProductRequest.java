package com.example.salemanagement.dto.request;

import com.example.salemanagement.Enum.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateProductRequest {
    private String productName;
    private BigDecimal price;
    private ProductStatus productStatus;
}
