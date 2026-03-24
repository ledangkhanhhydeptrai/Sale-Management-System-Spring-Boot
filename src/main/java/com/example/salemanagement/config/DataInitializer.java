package com.example.salemanagement.config;

import com.example.salemanagement.Enum.PlanType;
import com.example.salemanagement.Enum.StoreStatus;
import com.example.salemanagement.Enum.UserRole;
import com.example.salemanagement.Enum.UserStatus;
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

        // =========================
        // ROLE INIT
        // =========================
        Role adminRole = createRole(roleRepository, UserRole.ADMIN);
        Role userRole = createRole(roleRepository, UserRole.CUSTOMER);
        Role staffRole = createRole(roleRepository, UserRole.STAFF);
        Role wareHouseRole = createRole(roleRepository, UserRole.WAREHOUSE_MANAGER);

        // =========================
        // ADMIN USER INIT
        // =========================
        User adminUser = userRepository.findByEmail("admin@gmail.com")
                .orElseGet(() -> {
                    User u = User.builder()
                            .name("admin")
                            .email("admin@gmail.com")
                            .password(passwordEncoder.encode("123456"))
                            .role(adminRole)
                            .status(UserStatus.ACTIVE)
                            .image("https://res.cloudinary.com/dnmk3xqy0/image/upload/v1772962029/images_ogtcvo.jpg")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();

                    return userRepository.save(u);
                });

        // =========================
        // STORE INIT (DEMO STORE)
        // =========================
        Store store = storeRepository.findByCode("demo-store")
                .orElseGet(() -> {

                    Store newStore = new Store();
                    newStore.setName("Demo Store");
                    newStore.setCode("demo-store");
                    newStore.setPlan(PlanType.FREE);
                    newStore.setStatus(StoreStatus.ACTIVE);
                    newStore.setCreatedAt(LocalDateTime.now());
                    newStore.setUpdatedAt(LocalDateTime.now());

                    // 🔥 QUAN TRỌNG: STORE thuộc USER
                    newStore.setUser(adminUser);

                    return storeRepository.save(newStore);
                });

        // =========================
        // STAFF USER INIT
        // =========================
        if (!userRepository.existsByEmail("staff@gmail.com")) {
            User staff = User.builder()
                    .name("staff")
                    .email("staff@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(staffRole)
                    .status(UserStatus.ACTIVE)
                    .image("https://res.cloudinary.com/dnmk3xqy0/image/upload/v1772962072/images_1_eamypy.jpg")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            userRepository.save(staff);
        }

        // =========================
        // WAREHOUSE MANAGER INIT
        // =========================
        if (!userRepository.existsByEmail("warehouseManager@gmail.com")) {
            User warehouseManager = User.builder()
                    .name("warehouseManager")
                    .email("warehouseManager@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(wareHouseRole)
                    .status(UserStatus.ACTIVE)
                    .image("https://res.cloudinary.com/dnmk3xqy0/image/upload/v1772962107/360_F_461055011_7loYFoVN9ZnpZRCRJnoFgusfVMWacC4M_kb8o6f.jpg")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            userRepository.save(warehouseManager);
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