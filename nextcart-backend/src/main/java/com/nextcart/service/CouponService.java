package com.nextcart.service;

import com.nextcart.model.Coupon;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CouponService {
    Optional<Coupon> getCouponById(Integer id);
    Optional<Coupon> getCouponByCode(String couponCode);
    List<Coupon> getAllCoupons();
    List<Coupon> getActiveCoupons();
    Coupon createCoupon(Coupon coupon);
    Coupon updateCoupon(Coupon coupon);
    boolean deleteCoupon(Integer id);
    boolean isValidCoupon(String couponCode);
    BigDecimal calculateDiscount(String couponCode, BigDecimal orderAmount);
}
