package com.example.salemanagement.service.Interface;

import com.example.salemanagement.dto.request.CreateLoginRequest;
import com.example.salemanagement.dto.response.LoginResponse;

public interface LoginService {
    LoginResponse login(CreateLoginRequest request);
}
