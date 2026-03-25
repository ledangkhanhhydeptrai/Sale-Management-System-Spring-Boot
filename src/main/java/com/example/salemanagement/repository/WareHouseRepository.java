package com.example.salemanagement.repository;

import com.example.salemanagement.Enum.WareHouseStatus;
import com.example.salemanagement.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WareHouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findByStatus(WareHouseStatus status);
    List<Warehouse> findByStoreId(Long storeId);
    List<Warehouse> findByStore_Id(Long storeId);
}
