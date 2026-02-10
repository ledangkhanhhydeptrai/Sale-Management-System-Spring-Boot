package com.example.demo.service.Interface;

import com.example.demo.dto.request.CreateRegisterRequest;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService {
    void registerUser(CreateRegisterRequest request);
}
