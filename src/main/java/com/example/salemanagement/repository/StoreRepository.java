package com.example.salemanagement.repository;

import com.example.salemanagement.Enum.StoreStatus;
import com.example.salemanagement.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByCode(String code);

    boolean existsByName(String name);

    List<Store> findByUser_Id(Long userId);

    Optional<Store> findByUser_IdAndStatus(Long userId, StoreStatus status);
}
