package com.example.salemanagement.service.Interface;

import com.example.salemanagement.dto.request.CreateStoreRequest;
import com.example.salemanagement.dto.response.StoreResponse;
import com.example.salemanagement.entity.Store;
import com.example.salemanagement.response.ApiResponse;

public interface StoreService {
    Store createStore(String storeName);
    ApiResponse<StoreResponse> getMyStore();
    ApiResponse<StoreResponse> updateStoreStatus(Long storeId, CreateStoreRequest request);
}
