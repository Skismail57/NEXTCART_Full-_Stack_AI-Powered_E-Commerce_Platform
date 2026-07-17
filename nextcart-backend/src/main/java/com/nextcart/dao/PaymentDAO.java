package com.nextcart.dao;

import com.nextcart.model.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentDAO {
    Optional<Payment> findById(Integer paymentId);
    Optional<Payment> findByOrderId(Integer orderId);
    List<Payment> findAll();
    Payment create(Payment payment);
    Payment update(Payment payment);
    void delete(Integer paymentId);
}
