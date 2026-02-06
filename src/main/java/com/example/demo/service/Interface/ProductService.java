package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.response.ProductResponseAdmin;
import com.example.demo.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ApiResponse<List<ProductResponse>> getAllProduct();
    ApiResponse<List<ProductResponseAdmin>> getAllProductForAdmin();
    ApiResponse<ProductResponseAdmin> createProduct(CreateProductRequest request, MultipartFile file);
}
