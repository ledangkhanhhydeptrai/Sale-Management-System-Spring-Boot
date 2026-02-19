package com.example.salemanagement.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WareHouseResponse {
    private Long id;
    private String address;
    private String name;
    private StoreResponse store;
    private String city;
    private String district;
}
