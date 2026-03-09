package com.example.salemanagement.service.Implement;

import com.example.salemanagement.dto.request.CreateLoginRequest;
import com.example.salemanagement.dto.response.LoginResponse;
import com.example.salemanagement.entity.User;
import com.example.salemanagement.repository.UserRepository;
import com.example.salemanagement.security.JwtUtil;
import com.example.salemanagement.service.Interface.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(CreateLoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Sai email hoặc mật khẩu");
        }
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Email không tồn tại"));
        String jwt = jwtUtil.generateToken(user.getEmail());
        return LoginResponse.builder()
                .role(user.getRole().getName())
                .token(jwt)
                .name(user.getName())          // thêm
                .imageUrl(user.getImage())  // thêm
                .build();
    }
}
