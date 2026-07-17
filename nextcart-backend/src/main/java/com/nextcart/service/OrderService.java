package com.nextcart.service;

import com.nextcart.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> getOrderById(Integer id);
    Optional<Order> getOrderByOrderNumber(String orderNumber);
    List<Order> getOrdersByUserId(Integer userId, int page, int size);
    List<Order> getAllOrders(int page, int size);
    Order createOrder(Order order);
    Order updateOrder(Order order);
    Order updatePaymentStatus(Integer orderId, String paymentStatus);
    Order updateOrderStatus(Integer orderId, String orderStatus);
    boolean deleteOrder(Integer id);
    Order createOrderFromCart(Integer userId, Integer addressId, Integer couponId);
}
