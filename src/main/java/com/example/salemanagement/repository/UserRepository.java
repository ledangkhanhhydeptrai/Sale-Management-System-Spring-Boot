package com.example.salemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.salemanagement.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
            SELECT u FROM User u JOIN FETCH u.role WHERE u.email=:email
            """)
    Optional<User> findByUsername(@Param("email") String email);

    boolean existsByEmail(String email);
    boolean existsByName(String name);   // 👈 thêm dòng này
    Optional<User> findByEmail(String email);

}
