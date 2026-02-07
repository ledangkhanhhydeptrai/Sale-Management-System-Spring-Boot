package com.example.demo.service.Interface;

import com.example.demo.dto.response.FavouritesResponse;
import com.example.demo.entity.Product;
import com.example.demo.response.ApiResponse;

import java.util.List;

public interface FavouritesService {
    ApiResponse<Void> addToFavourites(Long productId);
    ApiResponse<List<FavouritesResponse>> getMyFavourites();
    ApiResponse<Void> removeFromFavourites(Long productId);

}
