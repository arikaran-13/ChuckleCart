package com.example.ecom.dto;

import com.example.ecom.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {

    private Long id;
    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID orderTrackId;
    private String userName;
    private List<CartItemDto> cartItems;
    private String couponName;

}
