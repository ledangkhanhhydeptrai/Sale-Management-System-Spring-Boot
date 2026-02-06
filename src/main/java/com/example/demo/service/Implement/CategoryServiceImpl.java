package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateCategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.mapper.CategoryMapper.CategoryMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ApiResponse<List<CategoryResponse>> getAllCategory() {
        List<CategoryResponse> responses = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategory)
                .toList();
        return ApiResponse.<List<CategoryResponse>>builder()
                .status(200)
                .message("Get All Category Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<CategoryResponse> createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        Category savedCategory = categoryRepository.save(category);
        CategoryResponse categoryResponse = categoryMapper.toCategory(savedCategory);
        return ApiResponse.<CategoryResponse>builder()
                .status(200)
                .message("Create Category Successfully")
                .data(categoryResponse)
                .build();
    }
}
