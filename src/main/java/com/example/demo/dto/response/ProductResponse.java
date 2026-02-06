package com.example.demo.dto.response;

import com.example.demo.Enum.ProductStatus;
import com.example.demo.Enum.StockStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private Double price;
    private String description;
    private String image;
    private String name;
    private CategoryResponse category;
}
