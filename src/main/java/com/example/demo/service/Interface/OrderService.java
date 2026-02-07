package com.example.demo.service.Interface;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;

import java.util.List;

public interface OrderService {
    Order checkout(User user, List<CartItem> cartItems);
}
