package com.example.ecom.controller.admin;

import com.example.ecom.entity.admin.Coupon;
import com.example.ecom.exception.ValidationException;
import com.example.ecom.service.admin.coupon.AdminCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/admin/coupon")
public class AdminCouponController {

    private final AdminCouponService couponService;

    @PostMapping
    public ResponseEntity<?>createCoupon(@RequestBody Coupon coupon){
      try{
           var createdCoupon = couponService.createCoupon(coupon);
           return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
      }catch (ValidationException ex){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
      }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Coupon>>getAllCoupon(){
        return ResponseEntity.status(HttpStatus.OK).body(couponService.getAllCoupon());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id")Long id){
        try {
            couponService.deleteCoupon(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
