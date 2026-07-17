package com.nextcart.service;

import com.nextcart.model.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Optional<Payment> getPaymentById(Integer id);
    Optional<Payment> getPaymentByOrderId(Integer orderId);
    List<Payment> getAllPayments();
    Payment createPayment(Payment payment);
    Payment updatePayment(Payment payment);
    void deletePayment(Integer id);
}
