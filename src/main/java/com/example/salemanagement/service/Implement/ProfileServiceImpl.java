package com.example.salemanagement.service.Implement;

import com.example.salemanagement.dto.response.UserResponse;
import com.example.salemanagement.entity.User;
import com.example.salemanagement.mapper.UserMapper.UserMapper;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.AuthService;
import com.example.salemanagement.service.Interface.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final AuthService authService;
    private final UserMapper userMapper;

    public ProfileServiceImpl(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse<UserResponse> getMyProfile() {
        User user = authService.getCurrentUser();
        UserResponse response = userMapper.userToResponse(user);
        return ApiResponse.<UserResponse>builder()
                .status(200)
                .message("Get Profile Successfully")
                .data(response)
                .build();
    }
}
