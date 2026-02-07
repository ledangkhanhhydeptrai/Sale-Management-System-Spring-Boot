package com.example.demo.service.Interface;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;

public interface OrderItemService {
    OrderItem createOrderItem(
            Order order,
            Product product,
            Long quantity
    );
}
