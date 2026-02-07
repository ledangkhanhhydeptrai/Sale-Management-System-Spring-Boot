package com.example.demo.controller;

import com.example.demo.dto.response.FavouritesResponse;
import com.example.demo.entity.Product;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Interface.FavouritesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Favourites")
@PreAuthorize("hasRole('USER')")
public class FavouritesController {
    private final FavouritesService favouritesService;

    public FavouritesController(FavouritesService favouritesService) {
        this.favouritesService = favouritesService;
    }

    @GetMapping("/favourites")
    public ResponseEntity<ApiResponse<List<FavouritesResponse>>> getAllFavorites() {
        return ResponseEntity.ok(favouritesService.getMyFavourites());
    }

    @PostMapping("/create/favorites/{productId}")
    public ResponseEntity<ApiResponse<Void>> createFavourites(@PathVariable Long productId) {
        return ResponseEntity.ok(favouritesService.addToFavourites(productId));
    }

    @DeleteMapping("/favourites/delete/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteFavourites(@PathVariable Long productId) {
        return ResponseEntity.ok(favouritesService.removeFromFavourites(productId));
    }
}
