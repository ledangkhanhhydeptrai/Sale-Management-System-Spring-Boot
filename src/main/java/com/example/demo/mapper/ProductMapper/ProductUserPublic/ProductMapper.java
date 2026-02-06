package com.example.demo.mapper.ProductMapper.ProductUserPublic;

import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toProductResponse(Product product) {
        if (product == null) {
            return null;
        }
        CategoryResponse categoryName = null;
        if (product.getCategory() != null) {
            categoryName = CategoryResponse.builder().
                    id(product.getCategory().getId())
                    .name(product.getCategory().getName())
                    .build();
        }
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .image(product.getImage())
                .price(product.getPrice())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .category(categoryName)
                .build();
    }
}
