package com.example.demo.mapper.ProductMapper.ProductAdmin;

import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponseAdmin;
import com.example.demo.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperAdmin {
    public ProductResponseAdmin toProductResponseAdmin(Product product) {
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
        return ProductResponseAdmin.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .status(product.getStatus())
                .stockStatus(product.getStockStatus())
                .category(categoryName)
                .build();
    }
}
