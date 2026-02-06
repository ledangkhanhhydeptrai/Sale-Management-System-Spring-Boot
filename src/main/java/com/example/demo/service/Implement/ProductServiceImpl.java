package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.response.ProductResponseAdmin;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper.ProductAdmin.ProductMapperAdmin;
import com.example.demo.mapper.ProductMapper.ProductUserPublic.ProductMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.CloudinaryService;
import com.example.demo.service.Interface.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductMapperAdmin productMapperAdmin;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductMapper productMapper, ProductMapperAdmin productMapperAdmin, ProductRepository productRepository, CloudinaryService cloudinaryService, CategoryRepository categoryRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.productMapperAdmin = productMapperAdmin;
        this.cloudinaryService = cloudinaryService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ApiResponse<List<ProductResponse>> getAllProduct() {
        List<ProductResponse> productResponses = productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
        return ApiResponse.<List<ProductResponse>>builder()
                .status(200)
                .message("Get All Product Successfully")
                .data(productResponses)
                .build();
    }

    @Override
    public ApiResponse<List<ProductResponseAdmin>> getAllProductForAdmin() {
        List<ProductResponseAdmin> responseAdmins = productRepository.findAll()
                .stream()
                .map(productMapperAdmin::toProductResponseAdmin)
                .toList();
        return ApiResponse.<List<ProductResponseAdmin>>builder()
                .status(200)
                .message("Get All Product Admin Successfully")
                .data(responseAdmins)
                .build();
    }

    @Override
    public ApiResponse<ProductResponseAdmin> createProduct(CreateProductRequest request, MultipartFile file) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("CATEGORY_NOT_FOUND"));
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setStatus(request.getStatus());
        product.setCategory(category);
        product.setStockStatus(request.getStockStatus());
        if (file != null && !file.isEmpty()) {
            try {
                String image = cloudinaryService.uploadFile(file);
                product.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Upload file thất bại");
            }
        }
        Product savedProduct = productRepository.save(product);
        ProductResponseAdmin productResponseAdmin = productMapperAdmin.toProductResponseAdmin(savedProduct);
        return ApiResponse.<ProductResponseAdmin>builder()
                .status(200)
                .message("Tạo sản phẩm thành công")
                .data(productResponseAdmin)
                .build();
    }
}
