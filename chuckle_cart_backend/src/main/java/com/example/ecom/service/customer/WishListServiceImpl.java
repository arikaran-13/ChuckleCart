package com.example.ecom.service.customer;

import com.example.ecom.dto.WishListDto;
import com.example.ecom.dto.WishListResponse;
import com.example.ecom.entity.admin.Product;
import com.example.ecom.entity.customer.User;
import com.example.ecom.entity.customer.WishList;
import com.example.ecom.exception.ValidationException;
import com.example.ecom.repository.AdminProductRepository;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService{

    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final AdminProductRepository adminProductRepository;

    @Override
    public WishListResponse addProductToWishList(WishListDto productDto) {
        WishList wl = new WishList();

        Optional<User> optionalUser = userRepository.findById(productDto.getUserId());
        if(optionalUser.isPresent()){
            wl.setUser(optionalUser.get());
        }else{
            throw new ValidationException("User not present");
        }

       Optional<WishList> optionalWishList = wishListRepository
               .findByProductIdAndUserId(productDto.getProductId(), productDto.getUserId());
       if(optionalWishList.isPresent()){
           return optionalWishList.get().getDto();
       }

        if(productDto.getProductId()!=null){
            Optional<Product> optionalProduct = adminProductRepository.findById(productDto.getProductId());
            if(optionalProduct.isPresent()){
                wl.setProduct(optionalProduct.get());
            }else{
                throw new ValidationException("Product not found");
            }
        }else{
            throw new ValidationException("Product id cannot be empty");
        }

        return wishListRepository.save(wl).getDto();
    }

    @Override
    public List<WishListResponse> getAllWishListForUser(Long userId) {
        if(userId==null){
            throw new ValidationException("User id not found");
        }
        return wishListRepository
                .findByUserId(userId)
                .stream()
                .map(WishList::getDto).toList();
    }

}
