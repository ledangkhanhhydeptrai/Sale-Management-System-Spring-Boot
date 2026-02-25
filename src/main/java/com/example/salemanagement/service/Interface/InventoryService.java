package com.example.salemanagement.service.Interface;

import com.example.salemanagement.dto.request.CreateInventoryRequest;
import com.example.salemanagement.dto.request.UpdateInventoryRequest;
import com.example.salemanagement.dto.response.InventoryResponse;
import com.example.salemanagement.response.ApiResponse;

import java.util.List;

public interface InventoryService {
    ApiResponse<List<InventoryResponse>> getAllInventory();
    ApiResponse<InventoryResponse> createInventory(CreateInventoryRequest request);
    ApiResponse<InventoryResponse> getInventoryById(Long id);
    ApiResponse<InventoryResponse> updateInventoryById(Long id, UpdateInventoryRequest request);
    ApiResponse<Void> deleteInventoryById(Long id);
}
