package com.example.demo.mapper.FavouritesMapper;

import com.example.demo.dto.response.FavouritesResponse;
import com.example.demo.entity.Favorites;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavouritesMapper {

    public List<FavouritesResponse> toFavouritesResponse(List<Favorites> favorites) {
        if (favorites == null || favorites.isEmpty()) {
            return List.of();
        }

        return favorites.stream()
                .map(f -> FavouritesResponse.builder()
                        .productId(f.getProduct().getId())
                        .name(f.getProduct().getName())
                        .price(f.getProduct().getPrice())
                        .image(f.getProduct().getImage())
                        .build()
                )
                .toList();
    }
}
