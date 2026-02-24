package com.example.salemanagement.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateWareHouseRequest {
    private String address;
    private String city;
    private String district;
    private String name;
}
