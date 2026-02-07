package com.example.demo.service.Implement;


import com.example.demo.dto.response.FavouritesResponse;
import com.example.demo.entity.Favorites;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.mapper.FavouritesMapper.FavouritesMapper;
import com.example.demo.repository.FavouritesRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.FavouritesService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouritesServiceImpl implements FavouritesService {

    private final UserRepository userRepository;
    private final FavouritesRepository favouritesRepository;
    private final FavouritesMapper favouritesMapper;
    private final ProductRepository productRepository;

    public FavouritesServiceImpl(UserRepository userRepository,
                                 FavouritesRepository favouritesRepository,
                                 FavouritesMapper favouritesMapper,
                                 ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.favouritesRepository = favouritesRepository;
        this.favouritesMapper = favouritesMapper;
        this.productRepository = productRepository;
    }

    // ===== LẤY USER HIỆN TẠI =====
    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ===== THÊM VÀO FAVOURITES =====
    @Override
    public ApiResponse<Void> addToFavourites(Long productId) {

        User user = getCurrentUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (favouritesRepository.existsByUserIdAndProductId(user.getId(), productId)) {
            throw new RuntimeException("Product already in favourites");
        }

        Favorites favorites = new Favorites();
        favorites.setUser(user);
        favorites.setProduct(product);

        favouritesRepository.save(favorites);

        return ApiResponse.<Void>builder()
                .status(200)
                .message("Add Favourites Successfully")
                .data(null)
                .build();
    }


    // ===== LẤY FAVOURITES CỦA USER =====
    @Override
    public ApiResponse<List<FavouritesResponse>> getMyFavourites() {
        User user = getCurrentUser();

        List<Favorites> favorites =
                favouritesRepository.findAllByUserId(user.getId());

        List<FavouritesResponse> responses =
                favouritesMapper.toFavouritesResponse(favorites);

        return ApiResponse.<List<FavouritesResponse>>builder()
                .status(200)
                .message("Get Favourites Successfully")
                .data(responses)
                .build();
    }

    // ===== XOÁ FAVOURITES =====
    @Override
    public ApiResponse<Void> removeFromFavourites(Long productId) {
        User user = getCurrentUser();

        favouritesRepository.deleteByUserIdAndProductId(user.getId(), productId);

        return ApiResponse.<Void>builder()
                .status(200)
                .message("Delete the favourites successfully")
                .data(null)
                .build();
    }
}
