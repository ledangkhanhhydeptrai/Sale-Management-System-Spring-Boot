package com.example.salemanagement.service.Implement;

import com.example.salemanagement.Enum.UserRole;
import com.example.salemanagement.dto.request.CreateRegisterRequest;
import com.example.salemanagement.entity.Role;
import com.example.salemanagement.entity.Store;
import com.example.salemanagement.entity.User;
import com.example.salemanagement.exception.BadRequestException;
import com.example.salemanagement.repository.RoleRepository;
import com.example.salemanagement.repository.StoreRepository;
import com.example.salemanagement.repository.UserRepository;
import com.example.salemanagement.service.Interface.CloudinaryService;
import com.example.salemanagement.service.Interface.RegisterService;
import com.example.salemanagement.service.Interface.StoreService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;
    private final StoreService storeService;

    public RegisterServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService, StoreService storeService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
        this.storeService = storeService;
    }

    @Override
    public void registerUser(CreateRegisterRequest request) {

        Map<String, String> errors = new HashMap<>();

        String email = request.getEmail() == null ? null : request.getEmail().trim().toLowerCase();
        String username = request.getName() == null ? null : request.getName().trim().toLowerCase();
        MultipartFile file = request.getFile();

        // ===== Validate email =====
        if (email == null || email.isBlank() || email.equals("string")) {
            errors.put("email", "Email không được để trống");
        } else if (userRepository.existsByEmail(email)) {
            errors.put("email", "Email đã tồn tại");
        }

        // ===== Validate username =====
        if (username == null || username.isBlank() || username.equals("string")) {
            errors.put("name", "Tên người dùng không được để trống");
        } else if (userRepository.existsByName(username)) {
            errors.put("name", "Tên người dùng đã tồn tại");
        }

        // ===== Validate password =====
        if (request.getPassword() == null
                || request.getPassword().isBlank()
                || request.getPassword().equals("string")) {
            errors.put("password", "Mật khẩu không hợp lệ");
        }

        // Nếu có lỗi → throw 1 lần
        if (!errors.isEmpty()) {
            throw new BadRequestException("Dữ liệu không hợp lệ", errors);
        }


        // ===== Role =====
        Role role = roleRepository.findByName(UserRole.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("ROLE_NOT_FOUND"));

        // ===== User =====
        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        // ===== Upload image =====
        if (file != null && !file.isEmpty()) {
            try {
                String image = cloudinaryService.uploadFile(file);
                user.setImage(image);
            } catch (IOException e) {
                throw new RuntimeException("Upload file thất bại");
            }
        }
        userRepository.save(user);
    }
}
