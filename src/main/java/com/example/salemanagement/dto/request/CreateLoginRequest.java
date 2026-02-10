package com.example.salemanagement.dto.request;

import lombok.Data;

@Data
public class CreateLoginRequest {
    private String email;
    private String password;
}
