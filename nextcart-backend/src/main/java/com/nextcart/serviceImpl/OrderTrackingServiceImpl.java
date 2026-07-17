package com.nextcart.serviceImpl;

import com.nextcart.dao.OrderTrackingDAO;
import com.nextcart.daoImpl.OrderTrackingDAOImpl;
import com.nextcart.model.OrderTracking;
import com.nextcart.service.OrderTrackingService;
import java.util.List;
import java.util.Optional;

public class OrderTrackingServiceImpl implements OrderTrackingService {
    private final OrderTrackingDAO orderTrackingDAO = new OrderTrackingDAOImpl();

    @Override
    public Optional<OrderTracking> getOrderTrackingById(Integer id) {
        return orderTrackingDAO.findById(id);
    }

    @Override
    public List<OrderTracking> getOrderTrackingByOrderId(Integer orderId) {
        return orderTrackingDAO.findByOrderId(orderId);
    }

    @Override
    public List<OrderTracking> getAllOrderTrackings() {
        return orderTrackingDAO.findAll();
    }

    @Override
    public OrderTracking createOrderTracking(OrderTracking orderTracking) {
        return orderTrackingDAO.create(orderTracking);
    }

    @Override
    public OrderTracking updateOrderTracking(OrderTracking orderTracking) {
        return orderTrackingDAO.update(orderTracking);
    }

    @Override
    public void deleteOrderTracking(Integer id) {
        orderTrackingDAO.delete(id);
    }
}
