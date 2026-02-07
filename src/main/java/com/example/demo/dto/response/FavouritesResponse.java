package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FavouritesResponse {
    private Long productId;
    private String name;
    private BigDecimal price;
    private String image;
}
