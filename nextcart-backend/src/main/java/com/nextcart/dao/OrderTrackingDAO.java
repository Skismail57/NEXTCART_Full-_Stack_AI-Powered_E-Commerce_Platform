package com.nextcart.dao;

import com.nextcart.model.OrderTracking;
import java.util.List;
import java.util.Optional;

public interface OrderTrackingDAO {
    Optional<OrderTracking> findById(Integer trackingId);
    List<OrderTracking> findByOrderId(Integer orderId);
    List<OrderTracking> findAll();
    OrderTracking create(OrderTracking orderTracking);
    OrderTracking update(OrderTracking orderTracking);
    void delete(Integer trackingId);
}
