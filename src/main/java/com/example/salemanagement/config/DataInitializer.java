package com.example.salemanagement.config;

import com.example.salemanagement.Enum.StoreStatus;
import com.example.salemanagement.Enum.UserRole;
import com.example.salemanagement.entity.Role;
import com.example.salemanagement.entity.Store;
import com.example.salemanagement.entity.User;
import com.example.salemanagement.repository.RoleRepository;
import com.example.salemanagement.repository.StoreRepository;
import com.example.salemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final StoreRepository storeRepository;

    @Override
    public void run(String... args) {
        Store store = Store.builder()
                .name("Demo Store")
                .code("demo-store")
                .plan("FREE")
                .status(StoreStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();
        store = storeRepository.save(store);
        // ✅ Tạo role
        Role adminRole = createRole(roleRepository, UserRole.ADMIN);
        Role userRole = createRole(roleRepository, UserRole.CUSTOMER);
        Role staffRole = createRole(roleRepository, UserRole.STAFF);
        // ✅ Tạo admin account
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            User adminUser = User.builder()
                    .name("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .role(adminRole)
                    .store(store)
                    .build();

            userRepository.save(adminUser);
        }
        if (!userRepository.existsByEmail("staff@gmail.com")) {
            User adminUser = User.builder()
                    .name("staff")
                    .email("staff@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .role(adminRole)
                    .store(store)
                    .build();

            userRepository.save(adminUser);
        }
    }

    private Role createRole(RoleRepository repo, UserRole name) {
        return repo.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(name);
                    return repo.save(role);
                });
    }
}
