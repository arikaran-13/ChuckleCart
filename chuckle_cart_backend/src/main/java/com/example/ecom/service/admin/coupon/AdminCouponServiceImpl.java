package com.example.ecom.service.admin.coupon;

import com.example.ecom.dto.CouponDto;
import com.example.ecom.entity.admin.Coupon;
import com.example.ecom.exception.ValidationException;
import com.example.ecom.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService{

    private final CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon){
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon code already exist");
        }

        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> getAllCoupon() {
        return couponRepository.findAll();
    }

    @Override
    public void deleteCoupon(Long id) {
       if(couponRepository.findById(id).isPresent()) {
           couponRepository.deleteById(id);
       }
       else{
           throw new NoSuchElementException("Coupon not found for id : "+id);
       }
    }
}
