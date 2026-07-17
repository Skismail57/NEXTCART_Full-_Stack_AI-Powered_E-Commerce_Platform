package com.nextcart.serviceImpl;

import com.nextcart.dao.TicketMessageDAO;
import com.nextcart.daoImpl.TicketMessageDAOImpl;
import com.nextcart.model.TicketMessage;
import com.nextcart.service.TicketMessageService;

import java.util.List;
import java.util.Optional;

public class TicketMessageServiceImpl implements TicketMessageService {
    private final TicketMessageDAO ticketMessageDAO = new TicketMessageDAOImpl();

    @Override
    public Optional<TicketMessage> getMessageById(Integer id) {
        return ticketMessageDAO.findById(id);
    }

    @Override
    public List<TicketMessage> getMessagesByTicketId(Integer ticketId) {
        return ticketMessageDAO.findByTicketId(ticketId);
    }

    @Override
    public TicketMessage createMessage(TicketMessage ticketMessage) {
        return ticketMessageDAO.create(ticketMessage);
    }
}
