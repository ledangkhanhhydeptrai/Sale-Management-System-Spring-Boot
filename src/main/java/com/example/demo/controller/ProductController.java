package com.example.demo.controller;

import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.response.ProductResponseAdmin;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.ProductService;
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
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ProductResponseAdmin>>> getAllProductForAdmin() {
        return ResponseEntity.ok(productService.getAllProductForAdmin());
    }

    @PostMapping(value = "/create/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponseAdmin>> createProduct(@ModelAttribute @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request, request.getFile()));
    }

    @GetMapping("/public/product/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductResponseById(id));
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponseAdmin>> getProductByIdForAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductByIdForAdmin(id));
    }

    @PutMapping(value = "/product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductResponseAdmin>> updateProductById(@PathVariable Long id, @ModelAttribute @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.updateProductResponseById(id, request, request.getFile()));
    }
    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProductResponseById(id));
    }
}
