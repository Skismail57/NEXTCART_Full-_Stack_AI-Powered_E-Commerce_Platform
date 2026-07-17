package com.nextcart.dao;

import com.nextcart.model.SupportTicket;

import java.util.List;
import java.util.Optional;

public interface SupportTicketDAO {
    Optional<SupportTicket> findById(Integer id);
    Optional<SupportTicket> findByTicketNumber(String ticketNumber);
    List<SupportTicket> findAll();
    List<SupportTicket> findByUserId(Integer userId);
    List<SupportTicket> findByAssignedTo(Integer assignedTo);
    List<SupportTicket> findByStatus(String status);
    SupportTicket create(SupportTicket supportTicket);
    SupportTicket update(SupportTicket supportTicket);
}
