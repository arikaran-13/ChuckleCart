package com.example.ecom.service.customer.cart;

import com.example.ecom.dto.AddProductToCartDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.dto.PlaceOrderDto;
import com.example.ecom.dto.UpdateCartItemsForActiveOrder;
import com.example.ecom.entity.admin.Coupon;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductToCartDto addProductToCartDto);
    OrderDto getCartByUserId(Long id);
    OrderDto applyCoupon(Long userId,String couponCode);
    List<Coupon> getAllCoupons();
    OrderDto updateCartItems(UpdateCartItemsForActiveOrder updateOrderRequest);
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
    List<OrderDto>getMyPlacedOrders(Long userId);
}
