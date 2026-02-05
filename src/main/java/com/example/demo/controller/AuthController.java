package com.example.demo.controller;

import com.example.demo.dto.request.CreateRegisterRequest;
import com.example.demo.response.ApiResponse;
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


    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
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
}
