package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavouritesResponse {
    private Long productId;
    private String name;
    private Double price;
    private String image;
}
