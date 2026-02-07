package com.example.demo.service.Implement;

import com.example.demo.Enum.OrderStatus;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.Interface.OrderItemService;
import com.example.demo.service.Interface.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
    }

    @Transactional
    public Order checkout(User user, List<CartItem> cartItems) {

        // 1. Tạo Order trước
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order); // 👈 sinh order_id

        // 2. Tạo OrderItem
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            OrderItem orderItem = orderItemService.createOrderItem(
                    order,
                    item.getProduct(),
                    item.getQuantity()
            );

            total = total.add(
                    orderItem.getPrice()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity()))
            );
        }

        // 3. Update total price
        order.setTotalPrice(total);
        return orderRepository.save(order);
    }
}

