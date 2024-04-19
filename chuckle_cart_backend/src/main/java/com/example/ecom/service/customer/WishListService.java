package com.example.ecom.service.customer;

import com.example.ecom.dto.WishListDto;
import com.example.ecom.dto.WishListResponse;

import java.util.List;

public interface WishListService {

    WishListResponse addProductToWishList(WishListDto productDto);

    List<WishListResponse> getAllWishListForUser(Long userId);
}
