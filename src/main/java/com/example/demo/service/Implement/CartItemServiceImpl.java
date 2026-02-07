package com.example.demo.service.Implement;

import com.example.demo.dto.response.CartItemResponse;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.mapper.CartItemMapper.CartItemMapper;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;

    public CartItemServiceImpl(CartItemRepository cartItemRepository,
                               ProductRepository productRepository,
                               UserRepository userRepository, CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartItemMapper = cartItemMapper;
    }

    // ================= ADD TO CART =================
    @Override
    public ApiResponse<Void> addToCart(Long userId, Long productId, Long quantity) {

        CartItem cartItem = cartItemRepository
                .findByUserIdAndProductId(userId, productId)
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        cartItemRepository.save(cartItem);

        return ApiResponse.<Void>builder()
                .status(200)
                .message("Add Cart Successfully")
                .data(null)
                .build();
    }

    // ================= GET MY CART =================
    @Override
    public ApiResponse<List<CartItemResponse>> getMyCart(Long userId) {

        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);

        List<CartItemResponse> responses = cartItems.stream()
                .map(cartItemMapper::toResponse)
                .toList();

        return ApiResponse.<List<CartItemResponse>>builder()
                .status(200)
                .message("Get Cart Successfully")
                .data(responses)
                .build();
    }

    // ================= UPDATE QUANTITY =================
    @Override
    public ApiResponse<Void> updateQuantity(Long cartItemId, Long quantity) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return ApiResponse.<Void>builder()
                .status(200)
                .message("Update quantity successfully")
                .data(null)
                .build();
    }

    // ================= REMOVE FROM CART =================
    @Override
    public ApiResponse<Void> removeFromCart(Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(cartItem);

        return ApiResponse.<Void>builder()
                .status(200)
                .message("Remove from cart successfully")
                .data(null)
                .build();
    }
}
