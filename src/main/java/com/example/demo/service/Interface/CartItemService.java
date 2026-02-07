package com.example.demo.service.Interface;

import com.example.demo.dto.response.CartItemResponse;
import com.example.demo.entity.CartItem;
import com.example.demo.response.ApiResponse;

import java.util.List;

public interface CartItemService {
    ApiResponse<Void> addToCart(Long userId, Long productId, Long quantity);
    ApiResponse<Void> updateQuantity(Long cartItemId, Long quantity);
    ApiResponse<List<CartItemResponse>> getMyCart(Long userId);
    ApiResponse<Void> removeFromCart(Long cartItemId);
}
