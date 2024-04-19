package com.example.ecom.repository;

import com.example.ecom.entity.admin.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    boolean existsByCode(String couponCode);

    Optional<Coupon> findByCode(String code);

}
