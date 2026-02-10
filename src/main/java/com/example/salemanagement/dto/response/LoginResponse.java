package com.example.salemanagement.dto.response;

import com.example.salemanagement.Enum.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token; // chỉ có khi dev
    private UserRole role;
}
