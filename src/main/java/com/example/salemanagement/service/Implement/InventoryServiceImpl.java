package com.example.salemanagement.service.Implement;

import com.example.salemanagement.Enum.InventoryStatus;
import com.example.salemanagement.dto.request.CreateInventoryRequest;
import com.example.salemanagement.dto.request.UpdateInventoryRequest;
import com.example.salemanagement.dto.response.InventoryResponse;
import com.example.salemanagement.entity.Inventory;
import com.example.salemanagement.entity.Product;
import com.example.salemanagement.entity.Warehouse;
import com.example.salemanagement.mapper.InventoryMapper.InventoryMapper;
import com.example.salemanagement.repository.InventoryRepository;
import com.example.salemanagement.repository.ProductRepository;
import com.example.salemanagement.repository.WareHouseRepository;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryMapper inventoryMapper;
    private final InventoryRepository inventoryRepository;
    private final WareHouseRepository wareHouseRepository;
    private final ProductRepository productRepository;

    public InventoryServiceImpl(InventoryMapper inventoryMapper,
                                InventoryRepository inventoryRepository,
                                WareHouseRepository wareHouseRepository,
                                ProductRepository productRepository) {
        this.inventoryMapper = inventoryMapper;
        this.inventoryRepository = inventoryRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ApiResponse<List<InventoryResponse>> getAllInventory() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        List<InventoryResponse> responses = inventoryList.stream()
                .map(inventoryMapper::toInventoryResponse)
                .toList();
        return ApiResponse.<List<InventoryResponse>>builder()
                .status(200)
                .message("Get All Inventory Successfully")
                .data(responses)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<InventoryResponse> createInventory(CreateInventoryRequest request) {
        Warehouse warehouse = wareHouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse Not Found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        Optional<Inventory> exists = inventoryRepository.findByWarehouseIdAndProductId(warehouse.getId(), product.getId());
        Inventory inventory;
        if (exists.isPresent()) {
            inventory = exists.get();
            inventory.setQuantity(inventory.getQuantity() + request.getQuantity());
        } else {
            inventory = new Inventory();
            inventory.setWarehouse(warehouse);
            inventory.setProduct(product);
            inventory.setQuantity(request.getQuantity());
        }
        Inventory savedInventory = inventoryRepository.save(inventory);
        InventoryResponse inventoryResponse = inventoryMapper.toInventoryResponse(savedInventory);
        return ApiResponse.<InventoryResponse>builder()
                .status(200)
                .message("Create Inventory Successfully")
                .data(inventoryResponse)
                .build();
    }

    @Override
    public ApiResponse<InventoryResponse> getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory Not Found"));
        InventoryResponse inventoryResponse = inventoryMapper.toInventoryResponse(inventory);
        return ApiResponse.<InventoryResponse>builder()
                .status(200)
                .message("Get Inventory Successfully")
                .data(inventoryResponse)
                .build();
    }

    @Override
    public ApiResponse<InventoryResponse> updateInventoryById(Long id, UpdateInventoryRequest request) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory Not Found"));
        inventory.setQuantity(request.getQuantity());
        inventory.setStatus(request.getStatus());
        Inventory savedInventory = inventoryRepository.save(inventory);
        InventoryResponse inventoryResponse = inventoryMapper.toInventoryResponse(savedInventory);
        return ApiResponse.<InventoryResponse>builder()
                .status(200)
                .message("Update Inventory Successfully")
                .data(inventoryResponse)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory Not Found"));
        inventoryRepository.delete(inventory);
        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Inventory Successfully")
                .data(null)
                .build();
    }
}
