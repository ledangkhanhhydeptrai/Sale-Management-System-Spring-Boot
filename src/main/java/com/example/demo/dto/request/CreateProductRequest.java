package com.example.demo.dto.request;

import com.example.demo.Enum.ProductStatus;
import com.example.demo.Enum.StockStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class CreateProductRequest {
    private String name;
    private Double price;
    private MultipartFile file;
    private String description;
    private StockStatus stockStatus;
    private ProductStatus status;
    private Long categoryId;
}
