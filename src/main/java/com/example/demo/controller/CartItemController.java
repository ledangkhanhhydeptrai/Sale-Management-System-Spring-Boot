package com.example.demo.controller;

import com.example.demo.dto.response.CartItemResponse;
import com.example.demo.entity.User;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.CartItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "CartItem")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    // ADD TO CART
    @PostMapping("/users/{userId}/cart")
    public ResponseEntity<ApiResponse<Void>> addToCart(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam Long quantity) {

        ApiResponse<Void> response =
                cartItemService.addToCart(userId, productId, quantity);

        return ResponseEntity.ok(response);
    }

    // GET MY CART
    @GetMapping("/users/{userId}/cart")
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> getMyCart(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                cartItemService.getMyCart(userId)
        );
    }


    // UPDATE QUANTITY
    @PutMapping("/cart-items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Long quantity) {

        return ResponseEntity.ok(
                cartItemService.updateQuantity(cartItemId, quantity)
        );
    }

    // REMOVE FROM CART
    @DeleteMapping("/cart-items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> removeFromCart(
            @PathVariable Long cartItemId) {

        return ResponseEntity.ok(
                cartItemService.removeFromCart(cartItemId)
        );
    }

}
