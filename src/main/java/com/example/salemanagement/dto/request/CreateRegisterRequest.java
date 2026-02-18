package com.example.salemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class CreateRegisterRequest {

    @NotBlank(message = "Tên không được để trống")

    private String name;

    @NotBlank(message = "Email không được để trống")

    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")

    private String password;

    private MultipartFile file;
}