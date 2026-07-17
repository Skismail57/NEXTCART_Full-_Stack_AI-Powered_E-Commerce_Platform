package com.nextcart.service;

import com.nextcart.model.SupportTicket;

import java.util.List;
import java.util.Optional;

public interface SupportTicketService {
    Optional<SupportTicket> getTicketById(Integer id);
    Optional<SupportTicket> getTicketByNumber(String ticketNumber);
    List<SupportTicket> getAllTickets();
    List<SupportTicket> getTicketsByUserId(Integer userId);
    List<SupportTicket> getTicketsByAssignedTo(Integer assignedTo);
    List<SupportTicket> getTicketsByStatus(String status);
    SupportTicket createTicket(SupportTicket supportTicket);
    SupportTicket updateTicket(SupportTicket supportTicket);
}
