package com.example.ecom.dto;

import lombok.Data;

@Data
public class AddProductToCartDto {

    private Long userId;
    private Long productId;
}
