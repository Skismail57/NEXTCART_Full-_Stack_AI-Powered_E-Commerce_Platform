package com.nextcart.serviceImpl;

import com.nextcart.dao.ExchangeRequestDAO;
import com.nextcart.daoImpl.ExchangeRequestDAOImpl;
import com.nextcart.model.ExchangeRequest;
import com.nextcart.service.ExchangeRequestService;
import java.util.List;
import java.util.Optional;

public class ExchangeRequestServiceImpl implements ExchangeRequestService {
    private final ExchangeRequestDAO exchangeRequestDAO = new ExchangeRequestDAOImpl();

    @Override
    public Optional<ExchangeRequest> getExchangeRequestById(Integer id) {
        return exchangeRequestDAO.findById(id);
    }

    @Override
    public List<ExchangeRequest> getAllExchangeRequests() {
        return exchangeRequestDAO.findAll();
    }

    @Override
    public List<ExchangeRequest> getExchangeRequestsByStatus(String status) {
        return exchangeRequestDAO.findByStatus(status);
    }

    @Override
    public ExchangeRequest createExchangeRequest(ExchangeRequest exchangeRequest) {
        return exchangeRequestDAO.create(exchangeRequest);
    }

    @Override
    public ExchangeRequest updateExchangeRequest(ExchangeRequest exchangeRequest) {
        return exchangeRequestDAO.update(exchangeRequest);
    }

    @Override
    public void deleteExchangeRequest(Integer id) {
        exchangeRequestDAO.delete(id);
    }
}
