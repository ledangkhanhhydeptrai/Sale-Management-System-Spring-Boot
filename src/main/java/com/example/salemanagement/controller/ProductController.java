package com.example.salemanagement.controller;

import com.example.salemanagement.dto.request.CreateProductRequest;
import com.example.salemanagement.dto.request.UpdateProductRequest;
import com.example.salemanagement.dto.response.ProductResponse;
import com.example.salemanagement.dto.response.ProductResponsePublic;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/public/product")
    public ResponseEntity<ApiResponse<List<ProductResponsePublic>>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/product/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllCustomer() {
        return ResponseEntity.ok(productService.getMyProduct());
    }

    @GetMapping("/product/customer/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<ProductResponse>> getMyCustomer(Long id) {
        return ResponseEntity.ok(productService.getMyProductById(id));
    }

    @PostMapping(value = "/create/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@ModelAttribute @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/public/product/{id}")
    public ResponseEntity<ApiResponse<ProductResponsePublic>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProductById(@PathVariable Long id, @ModelAttribute @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(productService.updateProductById(id, request));
    }
}
