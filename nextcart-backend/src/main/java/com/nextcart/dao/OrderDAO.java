package com.nextcart.dao;

import com.nextcart.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    Optional<Order> findById(Integer orderId);
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByUserId(Integer userId, int page, int size);
    List<Order> findAll(int page, int size);
    Order create(Order order);
    Order update(Order order);
    Order updatePaymentStatus(Integer orderId, String paymentStatus);
    Order updateOrderStatus(Integer orderId, String orderStatus);
    boolean delete(Integer orderId);
}
