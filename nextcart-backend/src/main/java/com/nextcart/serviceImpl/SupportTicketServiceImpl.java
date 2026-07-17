package com.nextcart.serviceImpl;

import com.nextcart.dao.SupportTicketDAO;
import com.nextcart.daoImpl.SupportTicketDAOImpl;
import com.nextcart.model.SupportTicket;
import com.nextcart.service.SupportTicketService;

import java.util.List;
import java.util.Optional;

public class SupportTicketServiceImpl implements SupportTicketService {
    private final SupportTicketDAO supportTicketDAO = new SupportTicketDAOImpl();

    @Override
    public Optional<SupportTicket> getTicketById(Integer id) {
        return supportTicketDAO.findById(id);
    }

    @Override
    public Optional<SupportTicket> getTicketByNumber(String ticketNumber) {
        return supportTicketDAO.findByTicketNumber(ticketNumber);
    }

    @Override
    public List<SupportTicket> getAllTickets() {
        return supportTicketDAO.findAll();
    }

    @Override
    public List<SupportTicket> getTicketsByUserId(Integer userId) {
        return supportTicketDAO.findByUserId(userId);
    }

    @Override
    public List<SupportTicket> getTicketsByAssignedTo(Integer assignedTo) {
        return supportTicketDAO.findByAssignedTo(assignedTo);
    }

    @Override
    public List<SupportTicket> getTicketsByStatus(String status) {
        return supportTicketDAO.findByStatus(status);
    }

    @Override
    public SupportTicket createTicket(SupportTicket supportTicket) {
        return supportTicketDAO.create(supportTicket);
    }

    @Override
    public SupportTicket updateTicket(SupportTicket supportTicket) {
        return supportTicketDAO.update(supportTicket);
    }
}
