package com.example.salemanagement.service.Interface;

import com.example.salemanagement.dto.request.CreateWareHouseRequest;
import com.example.salemanagement.dto.request.UpdateWareHouseRequest;
import com.example.salemanagement.dto.request.UpdateWareHouseRequestAdmin;
import com.example.salemanagement.dto.response.WareHouseResponse;
import com.example.salemanagement.response.ApiResponse;

import java.util.List;

public interface WareHouseService {
    ApiResponse<List<WareHouseResponse>> getAllWareHouse();
    ApiResponse<WareHouseResponse> createWareHouse(CreateWareHouseRequest request);
    ApiResponse<WareHouseResponse> getWareHouseById(Long id);
    ApiResponse<WareHouseResponse> updateWareHouse(Long id, UpdateWareHouseRequest request);
    ApiResponse<Void> deleteWarehouse(Long id);
    ApiResponse<WareHouseResponse> updateWareHouseForAdmin(Long id, UpdateWareHouseRequestAdmin request);
}
