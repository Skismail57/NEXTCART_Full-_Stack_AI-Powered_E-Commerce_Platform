package com.nextcart.serviceImpl;

import com.nextcart.dao.PaymentDAO;
import com.nextcart.daoImpl.PaymentDAOImpl;
import com.nextcart.model.Payment;
import com.nextcart.service.PaymentService;
import java.util.List;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public Optional<Payment> getPaymentById(Integer id) {
        return paymentDAO.findById(id);
    }

    @Override
    public Optional<Payment> getPaymentByOrderId(Integer orderId) {
        return paymentDAO.findByOrderId(orderId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentDAO.findAll();
    }

    @Override
    public Payment createPayment(Payment payment) {
        return paymentDAO.create(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return paymentDAO.update(payment);
    }

    @Override
    public void deletePayment(Integer id) {
        paymentDAO.delete(id);
    }
}
