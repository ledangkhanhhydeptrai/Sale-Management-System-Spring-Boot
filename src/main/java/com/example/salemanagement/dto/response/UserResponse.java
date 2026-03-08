package com.example.salemanagement.dto.response;

import com.example.salemanagement.Enum.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private UserStatus status;
    private String imageUrl;
}
