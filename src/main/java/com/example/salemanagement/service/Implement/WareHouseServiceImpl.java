package com.example.salemanagement.service.Implement;

import com.example.salemanagement.dto.response.WareHouseResponse;
import com.example.salemanagement.entity.Warehouse;
import com.example.salemanagement.mapper.WareHouseMapper.WareHouseMapper;
import com.example.salemanagement.repository.WareHouseRepository;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.WareHouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WareHouseServiceImpl implements WareHouseService {
    private final WareHouseRepository wareHouseRepository;
    private final WareHouseMapper wareHouseMapper;

    public WareHouseServiceImpl(WareHouseRepository wareHouseRepository, WareHouseMapper wareHouseMapper) {
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseMapper = wareHouseMapper;
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
}
