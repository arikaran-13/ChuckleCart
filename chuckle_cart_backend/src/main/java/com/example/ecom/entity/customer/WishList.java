package com.example.ecom.entity.customer;

import com.example.ecom.dto.WishListDto;
import com.example.ecom.dto.WishListResponse;
import com.example.ecom.entity.admin.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wishlist")
@Data
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    private String fav;

    public WishListResponse getDto(){
        var wishListResponse = new WishListResponse();
        wishListResponse.setId(id);
        wishListResponse.setProductId(product.getId());
        wishListResponse.setUserId(user.getId());
       wishListResponse.setImg(product.getImg());
       wishListResponse.setName(product.getName());
       wishListResponse.setPrice(product.getPrice());
       wishListResponse.setDescription(product.getDescription());
       wishListResponse.setCategoryName(product.getCategory().getName());
        wishListResponse.setFav(fav);
        return wishListResponse;
    }
}
