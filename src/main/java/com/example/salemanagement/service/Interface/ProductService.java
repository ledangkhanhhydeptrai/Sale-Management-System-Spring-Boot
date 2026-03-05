package com.example.salemanagement.service.Interface;

import com.example.salemanagement.dto.request.CreateProductRequest;
import com.example.salemanagement.dto.request.UpdateProductRequest;
import com.example.salemanagement.dto.response.ProductResponse;
import com.example.salemanagement.response.ApiResponse;

import java.util.List;

public interface ProductService {
    ApiResponse<List<ProductResponse>> getAllProduct();

    ApiResponse<ProductResponse> createProduct(CreateProductRequest request);

    ApiResponse<ProductResponse> getProductById(Long id);

    ApiResponse<ProductResponse> updateProductById(Long id, UpdateProductRequest request);

    ApiResponse<ProductResponse> getMyProductById(Long id);

    ApiResponse<List<ProductResponse>> getMyProduct();
}
