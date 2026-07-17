package com.nextcart.serviceImpl;

import com.nextcart.dao.CartDAO;
import com.nextcart.dao.CartItemDAO;
import com.nextcart.dao.OrderDAO;
import com.nextcart.daoImpl.CartDAOImpl;
import com.nextcart.daoImpl.CartItemDAOImpl;
import com.nextcart.daoImpl.OrderDAOImpl;
import com.nextcart.model.Cart;
import com.nextcart.model.CartItem;
import com.nextcart.model.Order;
import com.nextcart.service.OrderService;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final CartDAO cartDAO = new CartDAOImpl();
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();

    @Override
    public Optional<Order> getOrderById(Integer id) {
        return orderDAO.findById(id);
    }

    @Override
    public Optional<Order> getOrderByOrderNumber(String orderNumber) {
        return orderDAO.findByOrderNumber(orderNumber);
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId, int page, int size) {
        return orderDAO.findByUserId(userId, page, size);
    }

    @Override
    public List<Order> getAllOrders(int page, int size) {
        return orderDAO.findAll(page, size);
    }

    @Override
    public Order createOrder(Order order) {
        return orderDAO.create(order);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderDAO.update(order);
    }

    @Override
    public Order updatePaymentStatus(Integer orderId, String paymentStatus) {
        return orderDAO.updatePaymentStatus(orderId, paymentStatus);
    }

    @Override
    public Order updateOrderStatus(Integer orderId, String orderStatus) {
        return orderDAO.updateOrderStatus(orderId, orderStatus);
    }

    @Override
    public boolean deleteOrder(Integer id) {
        return orderDAO.delete(id);
    }

    @Override
    public Order createOrderFromCart(Integer userId, Integer addressId, Integer couponId) {
        Optional<Cart> cartOpt = cartDAO.findByUserId(userId);
        if (cartOpt.isEmpty()) {
            throw new IllegalArgumentException("Cart not found");
        }

        Cart cart = cartOpt.get();
        List<CartItem> cartItems = cartItemDAO.findByCartId(cart.getCartId());

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setAddressId(addressId);
        order.setCouponId(couponId);

        order.setSubtotal(cartItemDAO.getCartTotal(cart.getCartId()));
        order.setDiscount(java.math.BigDecimal.ZERO);
        order.setTax(java.math.BigDecimal.ZERO);
        order.setShippingCharge(java.math.BigDecimal.ZERO);
        order.setGrandTotal(order.getSubtotal());

        Order createdOrder = orderDAO.create(order);

        cartItemDAO.deleteByCartId(cart.getCartId());

        return createdOrder;
    }
}
