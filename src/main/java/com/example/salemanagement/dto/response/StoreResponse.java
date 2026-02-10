package com.example.salemanagement.dto.response;

import com.example.salemanagement.Enum.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponse {
    private Long id;
    private String name;
    private String code;
    private String plan;
    private StoreStatus status;
}
