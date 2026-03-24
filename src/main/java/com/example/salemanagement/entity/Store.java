package com.example.salemanagement.entity;

import com.example.salemanagement.Enum.PlanType;
import com.example.salemanagement.Enum.StoreStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "store")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StoreStatus status;
    @Column(name = "plan")
    @Enumerated(EnumType.STRING)
    private PlanType plan;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
