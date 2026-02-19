package com.example.salemanagement.mapper.WareHouseMapper;

import com.example.salemanagement.dto.response.StoreResponse;
import com.example.salemanagement.dto.response.WareHouseResponse;
import com.example.salemanagement.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WareHouseMapper {
    public WareHouseResponse toWareHouseResponse(Warehouse warehouse) {
        if (warehouse == null) {
            return null;
        }
        return WareHouseResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .city(warehouse.getCity())
                .district(warehouse.getDistrict())
                .store(StoreResponse.builder()
                        .id(warehouse.getStore().getId())
                        .name(warehouse.getStore().getName())
                        .createdAt(warehouse.getStore().getCreatedAt())
                        .updatedAt(warehouse.getStore().getUpdatedAt())
                        .code(warehouse.getStore().getCode())
                        .plan(warehouse.getStore().getPlan())
                        .status(warehouse.getStore().getStatus())
                        .build())
                .address(warehouse.getAddress())
                .build();
    }
}
