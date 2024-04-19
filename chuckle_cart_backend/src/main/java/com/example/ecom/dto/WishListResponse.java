package com.example.ecom.dto;

import lombok.Data;

@Data
public class WishListResponse {
    private Long id;
    private Long userId;
    private String name;
    private Long productId;
    private Long price;
    private String description;
    private byte[] img;
    private String categoryName;
    private String fav;
}
