package com.example.salemanagement.mapper.WareHouseMapper;

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
                .storeId(warehouse.getStore().getId())
                .address(warehouse.getAddress())
                .build();
    }
}
