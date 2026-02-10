package com.example.demo.service.Implement;

import com.example.demo.dto.request.CreateLoginRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.Interface.LoginService;
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
                .build();
    }
}
