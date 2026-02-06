package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateLoginRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.response.ApiResponse;

public interface LoginService {
    ApiResponse<LoginResponse> login(CreateLoginRequest request);
}
