package com.nextcart.orderservice.repository;

import com.nextcart.orderservice.model.OrderTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderTrackingRepository extends JpaRepository<OrderTracking, Integer> {
    List<OrderTracking> findByOrderId(Integer orderId);
}
