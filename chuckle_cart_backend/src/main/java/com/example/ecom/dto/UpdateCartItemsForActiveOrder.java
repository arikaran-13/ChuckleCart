package com.example.ecom.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpdateCartItemsForActiveOrder {
    private Long userId;
    private Long quantity;
    private Long productId;
}
