package com.example.salemanagement.service.Interface;

import com.example.salemanagement.dto.response.UserResponse;
import com.example.salemanagement.response.ApiResponse;

public interface ProfileService {
    ApiResponse<UserResponse> getMyProfile();
}
