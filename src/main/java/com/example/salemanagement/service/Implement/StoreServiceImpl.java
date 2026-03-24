package com.example.salemanagement.service.Implement;

import com.example.salemanagement.Enum.PlanType;
import com.example.salemanagement.Enum.StoreStatus;
import com.example.salemanagement.dto.request.CreateStoreRequest;
import com.example.salemanagement.dto.request.UpdateStoreRequest;
import com.example.salemanagement.dto.response.StoreResponse;
import com.example.salemanagement.entity.Store;
import com.example.salemanagement.entity.User;
import com.example.salemanagement.exception.BadRequestException;
import com.example.salemanagement.mapper.StoreMapper.StoreMapper;
import com.example.salemanagement.repository.StoreRepository;
import com.example.salemanagement.repository.UserRepository;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.security.UserPrincipal;
import com.example.salemanagement.service.Interface.AuthService;
import com.example.salemanagement.service.Interface.StoreService;
import com.example.salemanagement.utils.SlugUtil;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;
    private final UserRepository userRepository;
    private final AuthService authService;

    public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper, UserRepository userRepository, AuthService authService) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Override
    public ApiResponse<StoreResponse> createStore(CreateStoreRequest request) {

        User user = authService.getCurrentUser();

        Map<String, String> errors = new HashMap<>();

        if (request.getName() == null || request.getName().isBlank()) {
            errors.put("storeName", "Tên cửa hàng không được để trống");
        } else if (storeRepository.existsByName(request.getName())) {
            errors.put("storeName", "Tên cửa hàng này đã tồn tại");
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Dữ liệu cửa hàng không hợp lệ", errors);
        }

        Store store = new Store();
        store.setCode(SlugUtil.toSlug(request.getName()));
        store.setName(request.getName());
        store.setStatus(StoreStatus.PENDING);
        store.setPlan(PlanType.FREE);
        store.setCreatedAt(LocalDateTime.now());
        store.setUpdatedAt(LocalDateTime.now());

        // 🔥 QUAN TRỌNG: set user cho store
        store.setUser(user);

        Store savedStore = storeRepository.save(store);

        return ApiResponse.<StoreResponse>builder()
                .status(200)
                .message("Create Store Successfully")
                .data(storeMapper.storeToStoreResponse(savedStore))
                .build();
    }

    @Override
    public ApiResponse<List<StoreResponse>> getMyStore() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
        System.out.println("AUTH NAME: " + auth.getName());
        System.out.println("USER ID: " + user.getId());
        List<Store> stores = storeRepository.findByUser_Id(user.getId());

        if (stores == null) {
            throw new RuntimeException("STORE_NOT_FOUND");
        }
        List<StoreResponse> response = stores.stream().map(storeMapper::storeToStoreResponse)
                .toList();
        return ApiResponse.<List<StoreResponse>>builder()
                .status(200)
                .message("Lấy store thành công")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<List<StoreResponse>> getAllStoresForAdmin() {

        List<Store> stores = storeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        List<StoreResponse> response = stores.stream()
                .map(storeMapper::storeToStoreResponse)
                .toList();

        return ApiResponse.<List<StoreResponse>>builder()
                .status(200)
                .message("Get all stores success")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<StoreResponse> updateStoreStatus(Long storeId, UpdateStoreRequest request) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("STORE_NOT_FOUND"));
        store.setStatus(request.getStoreStatus());
        Store updatedStore = storeRepository.save(store);
        StoreResponse storeResponse = StoreResponse.builder()
                .id(updatedStore.getId())
                .code(updatedStore.getCode())
                .name(updatedStore.getName())
                .plan(updatedStore.getPlan())
                .status(updatedStore.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return ApiResponse.<StoreResponse>builder()
                .status(200)
                .message("Cập nhật store thành công")
                .data(storeResponse)
                .build();
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("STORE_NOT_FOUND"));
    }
}
