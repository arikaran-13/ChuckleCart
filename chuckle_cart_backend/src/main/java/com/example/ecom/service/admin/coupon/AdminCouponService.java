package com.example.ecom.service.admin.coupon;

import com.example.ecom.entity.admin.Coupon;

import java.util.List;

public interface AdminCouponService {
    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupon();

    void deleteCoupon(Long id);
}
