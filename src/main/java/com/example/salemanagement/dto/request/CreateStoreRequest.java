package com.example.salemanagement.dto.request;

import com.example.salemanagement.Enum.StoreStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStoreRequest {
    private StoreStatus storeStatus;
    private String storeName;
}
