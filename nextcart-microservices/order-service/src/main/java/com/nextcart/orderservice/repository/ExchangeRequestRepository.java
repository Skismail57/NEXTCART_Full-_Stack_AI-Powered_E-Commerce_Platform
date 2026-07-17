package com.nextcart.orderservice.repository;

import com.nextcart.orderservice.model.ExchangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, Integer> {
    List<ExchangeRequest> findByOrderItemId(Integer orderItemId);
}
