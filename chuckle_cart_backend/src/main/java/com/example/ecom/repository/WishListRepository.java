package com.example.ecom.repository;

import com.example.ecom.entity.customer.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long> {
    List<WishList>findByUserId(Long id);
    Optional<WishList> findByProductId(Long productId);
    Optional<WishList>findByProductIdAndUserId(Long productId,Long userId);
}
