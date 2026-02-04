package com.example.demo.entity;

import com.example.demo.Enum.TypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "type")
    private TypeEnum typeEnum;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
