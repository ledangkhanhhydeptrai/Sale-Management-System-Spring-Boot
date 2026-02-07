package com.example.demo.dto.request;

import com.example.demo.Enum.ProductStatus;
import com.example.demo.Enum.StockStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    private MultipartFile file;
    private String description;
    private StockStatus stockStatus;
    private ProductStatus status;
    private Integer quantity;
    private Long categoryId;
}
