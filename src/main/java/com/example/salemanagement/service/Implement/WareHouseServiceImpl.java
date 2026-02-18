package com.example.salemanagement.service.Implement;

import com.example.salemanagement.dto.request.CreateWareHouseRequest;
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
    public ApiResponse<WareHouseResponse> createWareHouse(CreateWareHouseRequest request) {

        User user = authService.getCurrentUser();
        Store store;
        System.out.println("USER STORE FROM TOKEN = " + user.getStore());
        // 🔥 Nếu là ADMIN toàn tổng
        if (user.getStore() == null) {

            if (request.getStoreId() == null) {
                throw new RuntimeException("STORE_ID_REQUIRED");
            }

            store = storeRepository.findById(request.getStoreId())
                    .orElseThrow(() -> new RuntimeException("STORE_NOT_FOUND"));

        } else {
            // 🔥 Nếu là admin / manager của 1 store
            store = user.getStore();
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setCity(request.getCity());
        warehouse.setDistrict(request.getDistrict());
        warehouse.setAddress(request.getAddress());
        warehouse.setStore(store);

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
}
