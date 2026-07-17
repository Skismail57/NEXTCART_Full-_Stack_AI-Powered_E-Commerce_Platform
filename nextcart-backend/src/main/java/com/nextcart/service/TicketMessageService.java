package com.nextcart.service;

import com.nextcart.model.TicketMessage;

import java.util.List;
import java.util.Optional;

public interface TicketMessageService {
    Optional<TicketMessage> getMessageById(Integer id);
    List<TicketMessage> getMessagesByTicketId(Integer ticketId);
    TicketMessage createMessage(TicketMessage ticketMessage);
}
