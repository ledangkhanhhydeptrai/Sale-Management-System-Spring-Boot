package com.example.demo.mapper.CartItemMapper;

import com.example.demo.dto.response.CartItemResponse;
import com.example.demo.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartItemMapper {

    public CartItemResponse toResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .productImage(cartItem.getProduct().getImage())
                .price(cartItem.getProduct().getPrice())
                .quantity(cartItem.getQuantity())
                .totalPrice(
                        cartItem.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
                )
                .build();
    }
}

