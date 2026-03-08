package com.example.salemanagement.mapper.UserMapper;

import com.example.salemanagement.dto.response.UserResponse;
import com.example.salemanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse userToResponse(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();
    }
}
