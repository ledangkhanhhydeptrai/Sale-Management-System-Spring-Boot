package com.example.salemanagement.mapper.ProductMapper;

import com.example.salemanagement.dto.response.ProductResponse;
import com.example.salemanagement.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toProductResponse(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .storeId(product.getStore().getId())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
