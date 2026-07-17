package com.nextcart.serviceImpl;

import com.nextcart.dao.CouponDAO;
import com.nextcart.daoImpl.CouponDAOImpl;
import com.nextcart.model.Coupon;
import com.nextcart.service.CouponService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CouponServiceImpl implements CouponService {
    private final CouponDAO couponDAO = new CouponDAOImpl();

    @Override
    public Optional<Coupon> getCouponById(Integer id) {
        return couponDAO.findById(id);
    }

    @Override
    public Optional<Coupon> getCouponByCode(String couponCode) {
        return couponDAO.findByCouponCode(couponCode);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponDAO.findAll();
    }

    @Override
    public List<Coupon> getActiveCoupons() {
        return couponDAO.findActive();
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {
        return couponDAO.create(coupon);
    }

    @Override
    public Coupon updateCoupon(Coupon coupon) {
        return couponDAO.update(coupon);
    }

    @Override
    public boolean deleteCoupon(Integer id) {
        return couponDAO.delete(id);
    }

    @Override
    public boolean isValidCoupon(String couponCode) {
        return couponDAO.isValid(couponCode);
    }

    @Override
    public BigDecimal calculateDiscount(String couponCode, BigDecimal orderAmount) {
        Optional<Coupon> couponOpt = getCouponByCode(couponCode);
        if (couponOpt.isEmpty()) {
            return BigDecimal.ZERO;
        }

        Coupon coupon = couponOpt.get();

        if (!isValidCoupon(couponCode)) {
            return BigDecimal.ZERO;
        }

        if (coupon.getMinimumAmount() != null && orderAmount.compareTo(coupon.getMinimumAmount()) < 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal discount;
        if ("PERCENTAGE".equals(coupon.getDiscountType())) {
            discount = orderAmount.multiply(coupon.getDiscountValue()).divide(BigDecimal.valueOf(100));
        } else {
            discount = coupon.getDiscountValue();
        }

        if (coupon.getMaximumDiscount() != null && discount.compareTo(coupon.getMaximumDiscount()) > 0) {
            discount = coupon.getMaximumDiscount();
        }

        return discount;
    }
}
