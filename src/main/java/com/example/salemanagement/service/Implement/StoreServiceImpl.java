package com.example.salemanagement.service.Implement;

import com.example.salemanagement.Enum.StoreStatus;
import com.example.salemanagement.dto.request.CreateStoreRequest;
import com.example.salemanagement.dto.response.StoreResponse;
import com.example.salemanagement.entity.Store;
import com.example.salemanagement.entity.User;
import com.example.salemanagement.exception.BadRequestException;
import com.example.salemanagement.mapper.StoreMapper.StoreMapper;
import com.example.salemanagement.repository.StoreRepository;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.security.UserPrincipal;
import com.example.salemanagement.service.Interface.StoreService;
import com.example.salemanagement.utils.SlugUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    @Override
    public Store createStore(String storeName) {
        Map<String, String> errors = new HashMap<>();
        if (storeName == null || storeName.isBlank()) {
            errors.put("storeName", "Tên cửa hàng không được để trống");
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Dữ liệu cửa hàng không hợp lệ", errors);
        }
        String storeCode = SlugUtil.toSlug(storeName);
        Store store = Store.builder()
                .name(storeName.trim())
                .code(storeCode)
                .plan("FREE")
                .status(StoreStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        return storeRepository.save(store);
    }

    @Override
    public ApiResponse<StoreResponse> getMyStore() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        User user = principal.getUser();

        Store store = user.getStore();
        if (store == null) {
            throw new RuntimeException("STORE_NOT_FOUND");
        }

        return ApiResponse.<StoreResponse>builder()
                .status(200)
                .message("Lấy store thành công")
                .data(storeMapper.storeToStoreResponse(store))
                .build();
    }

    @Override
    public ApiResponse<StoreResponse> updateStoreStatus(Long storeId, CreateStoreRequest request) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("STORE_NOT_FOUND"));
        store.setStatus(request.getStoreStatus());
        store.setName(request.getStoreName());
        Store updatedStore = storeRepository.save(store);
        StoreResponse storeResponse = StoreResponse.builder()
                .id(updatedStore.getId())
                .code(updatedStore.getCode())
                .name(updatedStore.getName())
                .plan(updatedStore.getPlan())
                .status(updatedStore.getStatus())
                .build();
        return ApiResponse.<StoreResponse>builder()
                .status(200)
                .message("Cập nhật store thành công")
                .data(storeResponse)
                .build();
    }
}
