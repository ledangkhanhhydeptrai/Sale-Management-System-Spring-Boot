package com.example.demo.controller;

import com.example.demo.dto.request.CreateCategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/category")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @PostMapping("/create/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CreateCategoryRequest request) {
        return ResponseEntity.ok(categoryService.createCategory(request));
    }
}
