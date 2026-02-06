package com.example.demo.controller;

import com.example.demo.dto.request.CreateLoginRequest;
import com.example.demo.dto.request.CreateRegisterRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.LoginService;
import com.example.demo.service.Interface.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> register(
            @ModelAttribute @RequestBody CreateRegisterRequest request) {

        registerService.registerUser(request, request.getFile());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .status(201)
                        .message("Tạo tài khoản thành công")
                        .build());
    }

    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<LoginResponse>> login(@ModelAttribute @RequestBody CreateLoginRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }
}
