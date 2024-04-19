package com.example.ecom.controller.customer;

import com.example.ecom.dto.*;
import com.example.ecom.entity.admin.Coupon;
import com.example.ecom.entity.customer.WishList;
import com.example.ecom.exception.ValidationException;
import com.example.ecom.service.customer.WishListService;
import com.example.ecom.service.customer.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;
    private final WishListService wishListService;

    @PostMapping("/cart")
    public ResponseEntity<?>addProductToCart(@RequestBody AddProductToCartDto addProductToCartDto){
        return cartService.addProductToCart(addProductToCartDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<OrderDto> getCartByUserId(@PathVariable("userId") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartByUserId(id));
    }

    @GetMapping("/coupon/{id}/{code}")
    public ResponseEntity<?>applyCoupon(@PathVariable("id")Long id,@PathVariable("code")String code){
        try{
           OrderDto orderDto =  cartService.applyCoupon(id,code);
           return ResponseEntity.status(HttpStatus.OK).body(orderDto);
        }catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/coupon/all")
    public ResponseEntity<List<Coupon>>getAllCoupons(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(cartService.getAllCoupons());
    }

    @PutMapping("/cart")
    public ResponseEntity<?>updateActiveOrderQuantity(@RequestBody UpdateCartItemsForActiveOrder updateOrderRequest){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    cartService.updateCartItems(updateOrderRequest));
        }catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<?>placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(cartService.placeOrder(placeOrderDto));
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<OrderDto>>getAllMyPlacedOrders(
            @PathVariable("userId")Long userId){
       return ResponseEntity.status(HttpStatus.OK)
               .body(cartService.getMyPlacedOrders(userId));
    }

    @PostMapping("/wishlist")
    public ResponseEntity<WishListResponse>saveToWishList(@RequestBody WishListDto wishListDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(wishListService.addProductToWishList(wishListDto));
    }

    @GetMapping("/user/wishlist/{userId}")
    public ResponseEntity<List<WishListResponse>>getWishListForParticularUser(@PathVariable("userId")Long userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(wishListService.getAllWishListForUser(userId));
    }
}
