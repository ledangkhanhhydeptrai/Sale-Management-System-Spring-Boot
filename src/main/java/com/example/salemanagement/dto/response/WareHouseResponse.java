package com.example.salemanagement.dto.response;

import com.example.salemanagement.Enum.WareHouseRequestStatus;
import com.example.salemanagement.Enum.WareHouseStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WareHouseResponse {
    private Long id;
    private String address;
    private String name;
    private StoreResponse store;
    private String city;
    private String district;
    private WareHouseStatus wareHouseStatus;
    private WareHouseRequestStatus wareHouseRequestStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
