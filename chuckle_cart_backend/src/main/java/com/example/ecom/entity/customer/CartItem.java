package com.example.ecom.entity.customer;

import com.example.ecom.dto.CartItemDto;
import com.example.ecom.entity.admin.Product;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Table
@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public CartItemDto getCartDto(){
        var cartItem = new CartItemDto();
        cartItem.setId(id);
        cartItem.setOrderId(order.getId());
        cartItem.setPrice(price);
        cartItem.setProductId(product.getId());
        cartItem.setQuantity(quantity);
        cartItem.setUserId(user.getId());
        cartItem.setProductName(product.getName());
        cartItem.setReturnedImg(product.getImg());
        return cartItem;
    }

}
