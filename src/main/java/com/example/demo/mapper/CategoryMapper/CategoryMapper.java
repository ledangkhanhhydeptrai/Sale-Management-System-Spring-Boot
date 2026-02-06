package com.example.demo.mapper.CategoryMapper;

import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse toCategory(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
