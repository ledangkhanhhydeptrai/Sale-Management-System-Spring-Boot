package com.example.salemanagement.controller;

import com.example.salemanagement.dto.request.CreateProductRequest;
import com.example.salemanagement.dto.request.UpdateProductRequest;
import com.example.salemanagement.dto.response.ProductResponse;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id (current store)")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductResponse>> updateProductById(@PathVariable Long id, @ModelAttribute @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(productService.updateProductById(id, request));
    }
}
