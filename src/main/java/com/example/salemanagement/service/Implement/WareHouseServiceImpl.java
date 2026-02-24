package com.example.salemanagement.service.Implement;

import com.example.salemanagement.Enum.UserRole;
import com.example.salemanagement.Enum.WareHouseStatus;
import com.example.salemanagement.dto.request.CreateWareHouseRequest;
import com.example.salemanagement.dto.request.UpdateWareHouseRequest;
import com.example.salemanagement.dto.response.WareHouseResponse;
import com.example.salemanagement.entity.Store;
import com.example.salemanagement.entity.User;
import com.example.salemanagement.entity.Warehouse;
import com.example.salemanagement.mapper.WareHouseMapper.WareHouseMapper;
import com.example.salemanagement.repository.StoreRepository;
import com.example.salemanagement.repository.WareHouseRepository;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.AuthService;
import com.example.salemanagement.service.Interface.WareHouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WareHouseServiceImpl implements WareHouseService {

    private final WareHouseRepository wareHouseRepository;
    private final WareHouseMapper wareHouseMapper;
    private final AuthService authService;
    private final StoreRepository storeRepository;

    public WareHouseServiceImpl(WareHouseRepository wareHouseRepository, WareHouseMapper wareHouseMapper, AuthService authService, StoreRepository storeRepository) {
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseMapper = wareHouseMapper;
        this.authService = authService;
        this.storeRepository = storeRepository;
    }

    @Override
    public ApiResponse<List<WareHouseResponse>> getAllWareHouse() {

        User user = authService.getCurrentUser();

        System.out.println("===== DEBUG START =====");
        System.out.println("Current user: " + user);

        if (user != null && user.getStore() != null) {
            System.out.println("User Store ID: " + user.getStore().getId());
        } else {
            System.out.println("User is SUPER_ADMIN or store is null");
        }

        List<Warehouse> warehouses;

        if (user.getStore() == null) {
            warehouses = wareHouseRepository.findAll();
            System.out.println("Fetching ALL warehouses");
        } else {
            warehouses = wareHouseRepository.findByStoreId(user.getStore().getId());
            System.out.println("Fetching warehouses by storeId");
        }

        System.out.println("Warehouses size: " + warehouses.size());
        System.out.println("Warehouses data: " + warehouses);
        System.out.println("===== DEBUG END =====");

        List<WareHouseResponse> responses = warehouses.stream()
                .map(wareHouseMapper::toWareHouseResponse)
                .toList();

        return ApiResponse.<List<WareHouseResponse>>builder()
                .status(200)
                .message("Get All WareHouse Successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<WareHouseResponse> createWareHouse(CreateWareHouseRequest request) {

        User user = authService.getCurrentUser();
        Store store;
        System.out.println("===== CREATE DEBUG =====");
        System.out.println("EMAIL FROM TOKEN = " + user.getEmail());
        System.out.println("USER ID = " + user.getId());
        System.out.println("STORE ID FROM TOKEN = " + user.getStore().getId());
        System.out.println("===== CREATE DEBUG END =====");
        // 🔥 Nếu là ADMIN toàn tổng
        if (user.getRole().getName().equals(UserRole.ADMIN)) {

            if (request.getStoreId() == null) {
                throw new RuntimeException("STORE_ID_REQUIRED");
            }

            store = storeRepository.findById(request.getStoreId())
                    .orElseThrow();

        } else {
            store = storeRepository.findById(user.getStore().getId())
                    .orElseThrow();
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setCity(request.getCity());
        warehouse.setDistrict(request.getDistrict());
        warehouse.setAddress(request.getAddress());
        warehouse.setStore(store);
        warehouse.setStatus(WareHouseStatus.ACTIVE);
        Warehouse savedWarehouse = wareHouseRepository.save(warehouse);

        return ApiResponse.<WareHouseResponse>builder()
                .status(200)
                .message("Create WareHouse Successfully")
                .data(wareHouseMapper.toWareHouseResponse(savedWarehouse))
                .build();
    }

    @Override
    public ApiResponse<WareHouseResponse> getWareHouseById(Long id) {
        Warehouse warehouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WareHouse not found"));
        WareHouseResponse wareHouseResponse = wareHouseMapper.toWareHouseResponse(warehouse);
        return ApiResponse.<WareHouseResponse>builder()
                .status(200)
                .message("Get WareHouse Successfully")
                .data(wareHouseResponse)
                .build();
    }

    @Override
    public ApiResponse<WareHouseResponse> updateWareHouse(Long id, UpdateWareHouseRequest request) {
        User user = authService.getCurrentUser();
        Warehouse warehouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WareHouse not found"));
        if (!warehouse.getStore().getId().equals(user.getStore().getId())) {
            throw new RuntimeException("You do not have permission");
        }
        warehouse.setName(request.getName());
        warehouse.setCity(request.getCity());
        warehouse.setDistrict(request.getDistrict());
        warehouse.setAddress(request.getAddress());
        Warehouse savedWarehouse = wareHouseRepository.save(warehouse);
        WareHouseResponse wareHouseResponse = wareHouseMapper.toWareHouseResponse(savedWarehouse);
        return ApiResponse.<WareHouseResponse>builder()
                .status(200)
                .message("Update WareHouse Successfully")
                .data(wareHouseResponse)
                .build();
    }
    @Override
    public ApiResponse<Void> deleteWarehouse(Long id) {

        User user = authService.getCurrentUser();

        Warehouse warehouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        // check quyền
        if (!warehouse.getStore().getId().equals(user.getStore().getId())) {
            throw new RuntimeException("You do not have permission");
        }

        warehouse.setStatus(WareHouseStatus.INACTIVE);

        wareHouseRepository.save(warehouse);

        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete Warehouse Successfully")
                .build();
    }
}
