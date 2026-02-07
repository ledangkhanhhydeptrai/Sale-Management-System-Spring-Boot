package com.example.demo.dto.response;

import com.example.demo.Enum.ProductStatus;
import com.example.demo.Enum.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseAdmin {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String image;
    private ProductStatus status;
    private StockStatus stockStatus;
    private CategoryResponse category;
}
