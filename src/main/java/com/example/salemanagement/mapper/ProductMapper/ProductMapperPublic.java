package com.example.salemanagement.mapper.ProductMapper;

import com.example.salemanagement.dto.response.ProductResponsePublic;
import com.example.salemanagement.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperPublic {
    public ProductResponsePublic toProductResponsePublic(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResponsePublic.builder()
                .id(product.getId())
                .name(product.getName())
                .createdAt(product.getCreatedAt())
                .price(product.getPrice())
                .images(product.getImages())
                .build();
    }
}
