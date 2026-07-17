package com.nextcart.dao;

import com.nextcart.model.ExchangeRequest;
import java.util.List;
import java.util.Optional;

public interface ExchangeRequestDAO {
    Optional<ExchangeRequest> findById(Integer exchangeId);
    List<ExchangeRequest> findAll();
    List<ExchangeRequest> findByStatus(String status);
    ExchangeRequest create(ExchangeRequest exchangeRequest);
    ExchangeRequest update(ExchangeRequest exchangeRequest);
    void delete(Integer exchangeId);
}
