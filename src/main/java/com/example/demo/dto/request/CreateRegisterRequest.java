package com.example.demo.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class CreateRegisterRequest {
    private String email;
    private String password;
    private MultipartFile file;
    private String name;
}
