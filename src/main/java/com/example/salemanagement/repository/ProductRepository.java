package com.example.salemanagement.repository;

import com.example.salemanagement.entity.Product;
import com.example.salemanagement.entity.Store;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndStore_Id(Long id, Long storeId);
    List<Product> findByStore_IdIn(List<Long> storeIds, Sort sort);
}
