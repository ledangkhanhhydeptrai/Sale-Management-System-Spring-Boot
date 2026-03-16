package com.example.salemanagement.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCustomerRequest {
    private String name;
    private String phone;
    private String email;
    private Long storeId;

}
