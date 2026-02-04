package com.example.demo.entity;

import com.example.demo.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderItem_id")
    private List<OrderItem> orderItems;
    @Column(name = "totalPrice")
    private BigDecimal totalPrice;
    @Column(name = "status")
    private OrderStatus orderStatus;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
