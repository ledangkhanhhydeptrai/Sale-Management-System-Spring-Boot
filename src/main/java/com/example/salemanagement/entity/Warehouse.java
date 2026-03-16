package com.example.salemanagement.entity;

import com.example.salemanagement.Enum.WareHouseRequestStatus;
import com.example.salemanagement.Enum.WareHouseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "warehouses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên kho: Kho Quận 7
    @Column(name = "name", nullable = false)
    private String name;

    // Thành phố: HCM
    @Column(name = "city", nullable = false)
    private String city;

    // Quận / huyện: Quận 7
    @Column(name = "district", nullable = false)
    private String district;

    // Địa chỉ chi tiết
    @Column(name = "address")
    private String address;

    // Kho thuộc về store nào
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WareHouseStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "ware_house_status")
    private WareHouseRequestStatus wareHouseRequestStatus;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

