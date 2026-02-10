package com.example.demo.entity;

import com.example.demo.Enum.InventoryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "status")
    private InventoryStatus status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
