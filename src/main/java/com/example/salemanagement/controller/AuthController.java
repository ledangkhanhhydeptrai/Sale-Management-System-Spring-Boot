package com.example.salemanagement.controller;

import com.example.salemanagement.dto.request.CreateLoginRequest;
import com.example.salemanagement.dto.request.CreateRegisterRequest;
import com.example.salemanagement.dto.response.LoginResponse;
import com.example.salemanagement.response.ApiResponse;
import com.example.salemanagement.service.Interface.LoginService;
import com.example.salemanagement.service.Interface.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {
    private final RegisterService registerService;
    private final LoginService loginService;

    public AuthController(RegisterService registerService, LoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @ModelAttribute CreateRegisterRequest request
    ) {

        registerService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .status(201)
                        .message("Tạo tài khoản thành công")
                        .build());
    }

    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @ModelAttribute CreateLoginRequest request) {

        // 1. Service chỉ trả JWT + user info
        LoginResponse loginResponse = loginService.login(request);

        // 2. Set JWT vào HttpOnly Cookie
        ResponseCookie cookie = ResponseCookie.from("access_token", loginResponse.getToken())
                .httpOnly(true)
                .secure(true) // true khi deploy HTTPS
                .sameSite("None")
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.<LoginResponse>builder()
                        .status(200)
                        .message("Đăng nhập thành công")
                        .data(loginResponse)
                        .build());
    }
}
