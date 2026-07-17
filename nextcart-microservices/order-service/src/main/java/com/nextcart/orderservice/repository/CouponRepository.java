package com.nextcart.orderservice.repository;

import com.nextcart.orderservice.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    Optional<Coupon> findByCouponCode(String couponCode);
}
