package com.example.demo.controller;

import com.example.demo.dto.request.CreateLoginRequest;
import com.example.demo.dto.request.CreateRegisterRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.LoginService;
import com.example.demo.service.Interface.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                .secure(false) // true khi deploy HTTPS
                .sameSite("Strict")
                .path("/")
                .maxAge(60 * 60)
                .build();

        // 3. KHÔNG trả token trong body
        loginResponse.setToken(null);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.<LoginResponse>builder()
                        .status(200)
                        .message("Đăng nhập thành công")
                        .data(loginResponse)
                        .build());
    }
}
