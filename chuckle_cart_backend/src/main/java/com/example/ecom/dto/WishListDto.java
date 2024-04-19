package com.example.ecom.dto;

import com.example.ecom.entity.admin.Product;
import lombok.Data;

@Data
public class WishListDto {
    private Long id;
    private Long userId;
    private Long  productId;
    private String fav;
}
