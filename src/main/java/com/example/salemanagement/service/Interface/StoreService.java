package com.example.salemanagement.service.Interface;

import com.example.salemanagement.dto.request.CreateStoreRequest;
import com.example.salemanagement.dto.request.UpdateStoreRequest;
import com.example.salemanagement.dto.response.StoreResponse;
import com.example.salemanagement.entity.Store;
import com.example.salemanagement.response.ApiResponse;

import java.util.List;

public interface StoreService {
    ApiResponse<StoreResponse> createStore(CreateStoreRequest request);
    ApiResponse<List<StoreResponse>> getMyStore();
    ApiResponse<StoreResponse> updateStoreStatus(Long storeId, UpdateStoreRequest request);
    Store getStoreById(Long id);
    ApiResponse<List<StoreResponse>> getAllStoresForAdmin();
}
