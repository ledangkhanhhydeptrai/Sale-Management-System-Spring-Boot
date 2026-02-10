package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private String storeName;

    private MultipartFile file;
}