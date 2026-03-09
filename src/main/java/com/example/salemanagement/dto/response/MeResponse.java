package com.example.salemanagement.dto.response;

import com.example.salemanagement.Enum.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeResponse {
    private String name;
    private UserRole role;
    private String imageUrl;
}