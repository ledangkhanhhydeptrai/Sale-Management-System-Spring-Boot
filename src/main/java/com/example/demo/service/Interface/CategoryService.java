package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateCategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.response.ApiResponse;

import java.util.List;

public interface CategoryService {
    ApiResponse<List<CategoryResponse>> getAllCategory();
    ApiResponse<CategoryResponse> createCategory(CreateCategoryRequest request);
}
