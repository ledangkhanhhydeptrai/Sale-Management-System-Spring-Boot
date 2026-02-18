package com.example.salemanagement.service.Interface;

import com.example.salemanagement.dto.response.WareHouseResponse;
import com.example.salemanagement.response.ApiResponse;

import java.util.List;

public interface WareHouseService {
    ApiResponse<List<WareHouseResponse>> getAllWareHouse();
}
