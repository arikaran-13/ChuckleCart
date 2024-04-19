package com.example.ecom.entity.customer;

import com.example.ecom.dto.OrderDto;
import com.example.ecom.entity.admin.Coupon;
import com.example.ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID orderTrackId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id",referencedColumnName = "id")
    private Coupon coupon;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order")
    private List<CartItem> cartItems;

    public OrderDto getOrderDto(){
        var orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setAmount(amount);
        orderDto.setAddress(address);
//        orderDto.setQuantity(quantity);
        orderDto.setTotalAmount(totalAmount);
        orderDto.setPayment(payment);
//        orderDto.setSingleQuantityPrice(singleQuantityPrice);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setOrderTrackId(orderTrackId);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setUserName(user.getName());
        if(coupon!=null){
            orderDto.setCouponName(coupon.getName());
        }

        return orderDto;
    }
}
