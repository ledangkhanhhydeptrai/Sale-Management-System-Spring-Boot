package com.example.salemanagement.service.Implement;

import com.example.salemanagement.Enum.UserRole;
import com.example.salemanagement.Enum.WareHouseRequestStatus;
import com.example.salemanagement.Enum.WareHouseStatus;
import com.example.salemanagement.dto.request.CreateWareHouseRequest;
import com.example.salemanagement.dto.request.UpdateWareHouseRequest;
import com.example.salemanagement.dto.request.UpdateWareHouseRequestAdmin;
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

import java.time.LocalDateTime;
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

        List<Warehouse> warehouses = wareHouseRepository.findAll();

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
    public ApiResponse<List<WareHouseResponse>> getStoreById(Long storeId) {
        User user = authService.getCurrentUser();
        boolean hasStore = user.getStores().stream().anyMatch(store -> store.getId().equals(storeId));
        if (!hasStore && !user.getRole().getName().equals(UserRole.ADMIN)) {
            throw new RuntimeException("FORBIDDEN");
        }
        List<Warehouse> warehouses;
        if (user.getRole().getName().equals(UserRole.ADMIN)) {
            warehouses = wareHouseRepository.findByStoreId(storeId);
        } else {
            // user chỉ xem warehouse của store mình chọn
            warehouses = wareHouseRepository.findByStore_Id(storeId);
        }
        List<WareHouseResponse> responses = warehouses.stream()
                .map(wareHouseMapper::toWareHouseResponse)
                .toList();

        return ApiResponse.<List<WareHouseResponse>>builder()
                .status(200)
                .message("Get Warehouses By Store Success")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<WareHouseResponse> createWareHouse(CreateWareHouseRequest request) {

        User user = authService.getCurrentUser();
        if (request.getStoreId() == null) {
            throw new RuntimeException("STORE_ID_REQUIRED");
        }
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("STORE_NOT_FOUND"));

        if (!user.getRole().getName().equals(UserRole.ADMIN)) {
            boolean hasStore = user.getStores().stream()
                    .anyMatch(s -> s.getId().equals(store.getId()));

            if (!hasStore) {
                throw new RuntimeException("FORBIDDEN_STORE");
            }
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setCity(request.getCity());
        warehouse.setDistrict(request.getDistrict());
        warehouse.setAddress(request.getAddress());
        warehouse.setStore(store);
        warehouse.setCreatedAt(LocalDateTime.now());
        warehouse.setUpdatedAt(LocalDateTime.now());
        warehouse.setStatus(WareHouseStatus.INACTIVE);
        warehouse.setWareHouseRequestStatus(WareHouseRequestStatus.PENDING);

        Warehouse saved = wareHouseRepository.save(warehouse);

        return ApiResponse.<WareHouseResponse>builder()
                .status(200)
                .message("Create WareHouse Successfully")
                .data(wareHouseMapper.toWareHouseResponse(saved))
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
        Store store = user.getStores().stream()
                .findFirst()
                .orElseThrow();
        if (!warehouse.getStore().getId().equals(store.getId())) {
            throw new RuntimeException("You do not have permission");
        }
        warehouse.setName(request.getName());
        warehouse.setCity(request.getCity());
        warehouse.setDistrict(request.getDistrict());
        warehouse.setAddress(request.getAddress());
        warehouse.setWareHouseRequestStatus(WareHouseRequestStatus.PENDING);
        Warehouse savedWarehouse = wareHouseRepository.save(warehouse);
        WareHouseResponse wareHouseResponse = wareHouseMapper.toWareHouseResponse(savedWarehouse);
        return ApiResponse.<WareHouseResponse>builder()
                .status(200)
                .message("Update WareHouse Successfully")
                .data(wareHouseResponse)
                .build();
    }

    @Override
    public ApiResponse<WareHouseResponse> updateWareHouseForAdmin(Long id, UpdateWareHouseRequestAdmin request) {
        User user = authService.getCurrentUser();
        Warehouse warehouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WareHouse not found"));
        Store store = user.getStores().stream()
                .findFirst()
                .orElseThrow();
        if (!user.getRole().getName().equals(UserRole.ADMIN)) {
            if (warehouse.getStore().getId().equals(store.getId())) {
                throw new RuntimeException("FORBIDDEN");
            }
        }
        warehouse.setWareHouseRequestStatus(request.getStatus());
        warehouse.setStatus(WareHouseStatus.ACTIVE);
        Warehouse savedWarehouse = wareHouseRepository.save(warehouse);
        WareHouseResponse wareHouseResponse = wareHouseMapper.toWareHouseResponse(savedWarehouse);
        return ApiResponse.<WareHouseResponse>builder()
                .status(200)
                .message("Update WareHouse For User Successfully")
                .data(wareHouseResponse)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteWarehouse(Long id) {

        User user = authService.getCurrentUser();

        Warehouse warehouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Store store = user.getStores().stream()
                .findFirst()
                .orElseThrow();
        // check quyền
        if (!warehouse.getStore().getId().equals(store.getId())) {
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
