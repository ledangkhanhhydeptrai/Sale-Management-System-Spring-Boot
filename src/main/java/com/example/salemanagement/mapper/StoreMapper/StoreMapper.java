package com.example.salemanagement.mapper.StoreMapper;

import com.example.salemanagement.dto.response.StoreResponse;
import com.example.salemanagement.entity.Store;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {
    public StoreResponse storeToStoreResponse(Store store) {
        if (store == null) {
            return null;
        }
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .code(store.getCode())
                .plan(store.getPlan())
                .status(store.getStatus())
                .createdAt(store.getCreatedAt())
                .build();
    }
}
