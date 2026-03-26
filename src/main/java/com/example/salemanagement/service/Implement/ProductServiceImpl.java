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
import com.example.salemanagement.repository.StoreRepository;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.AuthService;
import com.example.salemanagement.service.Interface.CloudinaryService;
import com.example.salemanagement.service.Interface.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductMapperPublic productMapperPublic;
    private final AuthService authService;
    private final CloudinaryService cloudinaryService;
    private final StoreRepository storeRepository;

    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductMapper productMapper,
            ProductMapperPublic productMapperPublic,
            AuthService authService,
            CloudinaryService cloudinaryService,
            StoreRepository storeRepository
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productMapperPublic = productMapperPublic;
        this.authService = authService;
        this.cloudinaryService = cloudinaryService;
        this.storeRepository = storeRepository;
    }

    // =========================
    // GET ALL PRODUCT (PUBLIC)
    // =========================
    @Override
    public ApiResponse<List<ProductResponsePublic>> getAllProduct() {

        List<Product> products =
                productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        List<ProductResponsePublic> responses = products.stream()
                .map(productMapperPublic::toProductResponsePublic)
                .toList();

        return ApiResponse.<List<ProductResponsePublic>>builder()
                .status(200)
                .message("Get All Product Successfully")
                .data(responses)
                .build();
    }

    // =========================
    // GET MY PRODUCT
    // =========================
    @Override
    public ApiResponse<List<ProductResponse>> getMyProduct() {

        User user = authService.getCurrentUser();

        List<Store> stores = storeRepository.findByUser_Id(user.getId());

        List<Long> storeIds = stores.stream()
                .map(Store::getId)
                .toList();

        List<Product> products =
                productRepository.findByStore_IdIn(
                        storeIds,
                        Sort.by(Sort.Direction.ASC, "id")
                );

        List<ProductResponse> responses = products.stream()
                .map(productMapper::toProductResponse)
                .toList();

        return ApiResponse.<List<ProductResponse>>builder()
                .status(200)
                .message("Get My Product Successfully")
                .data(responses)
                .build();
    }

    // =========================
    // GET PRODUCT BY ID (PUBLIC)
    // =========================
    @Override
    public ApiResponse<ProductResponsePublic> getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        return ApiResponse.<ProductResponsePublic>builder()
                .status(200)
                .message("Get Product By Id Successfully")
                .data(productMapperPublic.toProductResponsePublic(product))
                .build();
    }

    // =========================
    // CREATE PRODUCT
    // =========================
    @Override
    public ApiResponse<ProductResponse> createProduct(CreateProductRequest request) {
        User user = authService.getCurrentUser();
        Store store = storeRepository
                .findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("ACTIVE_STORE_NOT_FOUND"));
        System.out.println("Current User ID: " + user.getId());
        System.out.println("Store Owner ID: " + store.getUser().getId());
        System.out.println("Store ID: " + store.getId());
        if (!store.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("FORBIDDEN");
        }
        MultipartFile file = request.getFile();
        Product product = new Product();
        product.setName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setStore(store);

        try {
            if (file != null && !file.isEmpty()) {
                String imageUrl = cloudinaryService.uploadFile(file);
                product.setImages(imageUrl);
            }
        } catch (Exception e) {
            throw new RuntimeException("Upload File Error");
        }

        Product saved = productRepository.save(product);

        ProductResponse response = productMapper.toProductResponse(saved);

        return ApiResponse.<ProductResponse>builder()
                .status(200)
                .message("Create Product Successfully")
                .data(response)
                .build();
    }

    // =========================
    // GET MY PRODUCT BY ID
    // =========================
    @Override
    public ApiResponse<ProductResponse> getMyProductById(Long id) {

        User user = authService.getCurrentUser();

        Store store = storeRepository
                .findByUser_IdAndStatus(user.getId(), StoreStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("STORE_NOT_FOUND"));

        Product product = productRepository
                .findByIdAndStore_Id(id, store.getId())
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        return ApiResponse.<ProductResponse>builder()
                .status(200)
                .message("Get Product Successfully")
                .data(productMapper.toProductResponse(product))
                .build();
    }

    // =========================
    // UPDATE PRODUCT
    // =========================
    @Override
    public ApiResponse<ProductResponse> updateProductById(Long id, UpdateProductRequest request) {

        User user = authService.getCurrentUser();

        Store store = storeRepository
                .findByUser_IdAndStatus(user.getId(), StoreStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("STORE_NOT_FOUND"));

        Product product = productRepository
                .findByIdAndStore_Id(id, store.getId())
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        product.setName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setStatus(request.getProductStatus());

        Product saved = productRepository.save(product);

        return ApiResponse.<ProductResponse>builder()
                .status(200)
                .message("Update Product Successfully")
                .data(productMapper.toProductResponse(saved))
                .build();
    }
}