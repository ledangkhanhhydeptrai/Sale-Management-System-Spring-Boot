package com.example.salemanagement.service.Interface;

import com.example.salemanagement.dto.request.CreateLoginRequest;
import com.example.salemanagement.dto.response.LoginResponse;
import com.example.salemanagement.dto.response.MeResponse;

public interface LoginService {
    LoginResponse login(CreateLoginRequest request);
}
