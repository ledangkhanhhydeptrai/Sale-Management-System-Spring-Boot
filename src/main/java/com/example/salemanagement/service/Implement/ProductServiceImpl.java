package com.example.salemanagement.service.Implement;

import com.example.salemanagement.Enum.StoreStatus;
import com.example.salemanagement.dto.request.CreateProductRequest;
import com.example.salemanagement.dto.request.UpdateProductRequest;
import com.example.salemanagement.dto.response.ProductResponse;
import com.example.salemanagement.dto.response.ProductResponsePublic;
import com.example.salemanagement.entity.Product;
import com.example.salemanagement.entity.Store;
import com.example.salemanagement.entity.User;
import com.example.salemanagement.mapper.ProductMapper.ProductMapper;
import com.example.salemanagement.mapper.ProductMapper.ProductMapperPublic;
import com.example.salemanagement.repository.ProductRepository;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.AuthService;
import com.example.salemanagement.service.Interface.CloudinaryService;
import com.example.salemanagement.service.Interface.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuthService authService;
    private final ProductMapperPublic productMapperPublic;
    private final CloudinaryService cloudinaryService;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, AuthService authService, ProductMapperPublic productMapperPublic, CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.authService = authService;
        this.productMapperPublic = productMapperPublic;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public ApiResponse<List<ProductResponsePublic>> getAllProduct() {

        List<Product> products = productRepository.findAll();

        List<ProductResponsePublic> responses = products.stream()
                .map(productMapperPublic::toProductResponsePublic)
                .toList();

        return ApiResponse.<List<ProductResponsePublic>>builder()
                .status(200)
                .message("Get All Product Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<List<ProductResponse>> getMyProduct() {
        User user = authService.getCurrentUser();
        Store store = user.getStore();
        List<Product> products = productRepository.findByStore(store);
        List<ProductResponse> responses = products.stream()
                .map(productMapper::toProductResponse)
                .toList();
        return ApiResponse.<List<ProductResponse>>builder()
                .status(200)
                .message("Get All Product Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<ProductResponsePublic> getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        ProductResponsePublic productResponse = productMapperPublic.toProductResponsePublic(product);

        return ApiResponse.<ProductResponsePublic>builder()
                .status(200)
                .message("Get Product By Id Successfully")
                .data(productResponse)
                .build();
    }

    @Override
    public ApiResponse<ProductResponse> createProduct(CreateProductRequest request) {
        User user = authService.getCurrentUser();
        Store store = user.getStore();
        MultipartFile file = request.getFile();
        ProductResponse productResponse = null;
        if (store.getStatus() == StoreStatus.ACTIVE) {
            Product product = new Product();
            product.setName(request.getProductName());
            product.setPrice(request.getPrice());
            product.setStore(store);
            try {
                if (file != null && !file.isEmpty()) {
                    String images = cloudinaryService.uploadFile(file);
                    product.setImages(images);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Upload File Error");
            }
            Product savedProduct = productRepository.save(product);
            productResponse = ProductResponse.builder()
                    .id(savedProduct.getId())
                    .name(savedProduct.getName())
                    .price(savedProduct.getPrice())
                    .storeId(savedProduct.getStore().getId())
                    .status(savedProduct.getStatus())
                    .images(savedProduct.getImages())
                    .createdAt(savedProduct.getCreatedAt())
                    .build();
        } else {
            throw new RuntimeException("Store is not active");
        }
        return ApiResponse.<ProductResponse>builder()
                .status(200)
                .message("Create Product Successfully")
                .data(productResponse)
                .build();
    }

    @Override
    public ApiResponse<ProductResponse> getMyProductById(Long id) {
        User user = authService.getCurrentUser();
        Store store = user.getStore();
        Product product = productRepository.findByIdAndStoreId(id, store.getId()).orElseThrow(() -> new RuntimeException("Product Not Found"));
        ProductResponse productResponse = productMapper.toProductResponse(product);
        return ApiResponse.<ProductResponse>builder()
                .status(200)
                .message("Get Product By Id Successfully")
                .data(productResponse)
                .build();
    }

    @Override
    public ApiResponse<ProductResponse> updateProductById(Long id, UpdateProductRequest request) {
        User user = authService.getCurrentUser();
        Store store = user.getStore();
        Product product = productRepository.findByIdAndStoreId(id, store.getId()).orElseThrow(() -> new RuntimeException("Product Not Found"));
        product.setName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setStatus(request.getProductStatus());
        product.setStore(store);
        Product savedProduct = productRepository.save(product);
        ProductResponse productResponse = productMapper.toProductResponse(savedProduct);
        return ApiResponse.<ProductResponse>builder()
                .status(200)
                .message("Update Product Successfully")
                .data(productResponse)
                .build();
    }
}
