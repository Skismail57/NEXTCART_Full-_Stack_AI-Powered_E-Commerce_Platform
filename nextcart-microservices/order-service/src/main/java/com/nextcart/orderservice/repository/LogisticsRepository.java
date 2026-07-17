package com.nextcart.orderservice.repository;

import com.nextcart.orderservice.model.Logistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogisticsRepository extends JpaRepository<Logistics, Integer> {
    Optional<Logistics> findByOrderId(Integer orderId);
}
