package com.example.salemanagement.repository;

import com.example.salemanagement.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByCode(String code);
    Optional<Store> findByCode(String code);
}
