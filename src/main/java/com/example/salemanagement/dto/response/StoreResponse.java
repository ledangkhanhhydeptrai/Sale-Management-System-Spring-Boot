package com.example.salemanagement.dto.response;

import com.example.salemanagement.Enum.PlanType;
import com.example.salemanagement.Enum.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponse {
    private Long id;
    private String name;
    private String code;
    private PlanType plan;
    private StoreStatus status;
    private LocalDateTime createdAt;
}
