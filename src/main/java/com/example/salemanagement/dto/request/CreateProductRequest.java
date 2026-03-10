package com.example.salemanagement.dto.request;

import com.example.salemanagement.Enum.ProductStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProductRequest {
    private String productName;
    private BigDecimal price;
    private MultipartFile file;
}
