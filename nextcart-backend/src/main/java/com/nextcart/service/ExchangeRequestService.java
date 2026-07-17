package com.nextcart.service;

import com.nextcart.model.ExchangeRequest;
import java.util.List;
import java.util.Optional;

public interface ExchangeRequestService {
    Optional<ExchangeRequest> getExchangeRequestById(Integer id);
    List<ExchangeRequest> getAllExchangeRequests();
    List<ExchangeRequest> getExchangeRequestsByStatus(String status);
    ExchangeRequest createExchangeRequest(ExchangeRequest exchangeRequest);
    ExchangeRequest updateExchangeRequest(ExchangeRequest exchangeRequest);
    void deleteExchangeRequest(Integer id);
}
