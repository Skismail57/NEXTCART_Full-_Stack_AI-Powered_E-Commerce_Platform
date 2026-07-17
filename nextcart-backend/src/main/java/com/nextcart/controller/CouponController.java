package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Coupon;
import com.nextcart.service.CouponService;
import com.nextcart.serviceImpl.CouponServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/coupons")
@Produces(MediaType.APPLICATION_JSON)
public class CouponController {
    private final CouponService couponService = new CouponServiceImpl();

    @GET
    public Response getAllCoupons() {
        try {
            List<Coupon> coupons = couponService.getAllCoupons();
            return Response.ok(ApiResponse.success(coupons)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveCoupons() {
        try {
            List<Coupon> coupons = couponService.getActiveCoupons();
            return Response.ok(ApiResponse.success(coupons)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getCouponById(@PathParam("id") Integer id) {
        try {
            Optional<Coupon> coupon = couponService.getCouponById(id);
            if (coupon.isPresent()) {
                return Response.ok(ApiResponse.success(coupon.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Coupon not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validateCoupon(Map<String, Object> request) {
        try {
            String couponCode = (String) request.get("couponCode");
            BigDecimal orderAmount = new BigDecimal(request.get("orderAmount").toString());
            
            boolean valid = couponService.isValidCoupon(couponCode);
            BigDecimal discount = couponService.calculateDiscount(couponCode, orderAmount);
            
            return Response.ok(ApiResponse.success(Map.of("valid", valid, "discount", discount))).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCoupon(Coupon coupon) {
        try {
            Coupon createdCoupon = couponService.createCoupon(coupon);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdCoupon))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCoupon(@PathParam("id") Integer id, Coupon coupon) {
        try {
            coupon.setCouponId(id);
            Coupon updatedCoupon = couponService.updateCoupon(coupon);
            return Response.ok(ApiResponse.success(updatedCoupon)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteCoupon(@PathParam("id") Integer id) {
        try {
            boolean deleted = couponService.deleteCoupon(id);
            if (deleted) {
                return Response.ok(ApiResponse.success("Coupon deleted successfully")).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Coupon not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
