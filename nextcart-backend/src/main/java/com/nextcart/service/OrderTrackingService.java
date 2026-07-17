package com.nextcart.service;

import com.nextcart.model.OrderTracking;
import java.util.List;
import java.util.Optional;

public interface OrderTrackingService {
    Optional<OrderTracking> getOrderTrackingById(Integer id);
    List<OrderTracking> getOrderTrackingByOrderId(Integer orderId);
    List<OrderTracking> getAllOrderTrackings();
    OrderTracking createOrderTracking(OrderTracking orderTracking);
    OrderTracking updateOrderTracking(OrderTracking orderTracking);
    void deleteOrderTracking(Integer id);
}
