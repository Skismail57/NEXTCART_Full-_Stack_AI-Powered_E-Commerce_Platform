package com.nextcart.dao;

import com.nextcart.model.TicketMessage;

import java.util.List;
import java.util.Optional;

public interface TicketMessageDAO {
    Optional<TicketMessage> findById(Integer id);
    List<TicketMessage> findByTicketId(Integer ticketId);
    TicketMessage create(TicketMessage ticketMessage);
}
