package com.nextcart.dao;

import com.nextcart.model.Coupon;
import java.util.List;
import java.util.Optional;

public interface CouponDAO {
    Optional<Coupon> findById(Integer couponId);
    Optional<Coupon> findByCouponCode(String couponCode);
    List<Coupon> findAll();
    List<Coupon> findActive();
    Coupon create(Coupon coupon);
    Coupon update(Coupon coupon);
    boolean delete(Integer couponId);
    boolean isValid(String couponCode);
}
