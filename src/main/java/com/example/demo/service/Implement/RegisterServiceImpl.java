package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateRegisterRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Interface.CloudinaryService;
import com.example.demo.service.Interface.RegisterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    public RegisterServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
    }
   @Override
    public void registerUser(CreateRegisterRequest request, MultipartFile file){
        String email = request.getEmail().trim().toLowerCase();
        String name = request.getName().trim().toLowerCase();
        if(userRepository.existsByEmail(email)){

        }
   }
}
