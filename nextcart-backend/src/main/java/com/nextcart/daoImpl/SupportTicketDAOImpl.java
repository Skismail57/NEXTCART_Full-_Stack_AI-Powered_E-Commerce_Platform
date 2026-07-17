package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.SupportTicketDAO;
import com.nextcart.dao.TicketMessageDAO;
import com.nextcart.model.SupportTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupportTicketDAOImpl implements SupportTicketDAO {
    private static final Logger logger = LoggerFactory.getLogger(SupportTicketDAOImpl.class);
    private final TicketMessageDAO ticketMessageDAO = new TicketMessageDAOImpl();

    @Override
    public Optional<SupportTicket> findById(Integer id) {
        String sql = "SELECT * FROM support_tickets WHERE support_ticket_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                SupportTicket ticket = extractSupportTicket(rs);
                ticket.setMessages(ticketMessageDAO.findByTicketId(ticket.getSupportTicketId()));
                return Optional.of(ticket);
            }

        } catch (SQLException e) {
            logger.error("Error finding support ticket by id: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<SupportTicket> findByTicketNumber(String ticketNumber) {
        String sql = "SELECT * FROM support_tickets WHERE ticket_number = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ticketNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                SupportTicket ticket = extractSupportTicket(rs);
                ticket.setMessages(ticketMessageDAO.findByTicketId(ticket.getSupportTicketId()));
                return Optional.of(ticket);
            }

        } catch (SQLException e) {
            logger.error("Error finding support ticket by number: " + ticketNumber, e);
        }

        return Optional.empty();
    }

    @Override
    public List<SupportTicket> findAll() {
        List<SupportTicket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM support_tickets ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tickets.add(extractSupportTicket(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all support tickets", e);
        }

        return tickets;
    }

    @Override
    public List<SupportTicket> findByUserId(Integer userId) {
        List<SupportTicket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM support_tickets WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tickets.add(extractSupportTicket(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding support tickets by user id: " + userId, e);
        }

        return tickets;
    }

    @Override
    public List<SupportTicket> findByAssignedTo(Integer assignedTo) {
        List<SupportTicket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM support_tickets WHERE assigned_to = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, assignedTo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tickets.add(extractSupportTicket(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding support tickets by assigned to: " + assignedTo, e);
        }

        return tickets;
    }

    @Override
    public List<SupportTicket> findByStatus(String status) {
        List<SupportTicket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM support_tickets WHERE status = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tickets.add(extractSupportTicket(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding support tickets by status: " + status, e);
        }

        return tickets;
    }

    @Override
    public SupportTicket create(SupportTicket supportTicket) {
        String sql = "INSERT INTO support_tickets (user_id, assigned_to, ticket_number, subject, description, priority, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setObject(1, supportTicket.getUserId());
            stmt.setObject(2, supportTicket.getAssignedTo());
            stmt.setString(3, supportTicket.getTicketNumber());
            stmt.setString(4, supportTicket.getSubject());
            stmt.setString(5, supportTicket.getDescription());
            stmt.setString(6, supportTicket.getPriority() != null ? supportTicket.getPriority() : "MEDIUM");
            stmt.setString(7, supportTicket.getStatus() != null ? supportTicket.getStatus() : "OPEN");

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                supportTicket.setSupportTicketId(rs.getInt(1));
                return findById(supportTicket.getSupportTicketId()).orElse(supportTicket);
            }

        } catch (SQLException e) {
            logger.error("Error creating support ticket", e);
        }

        return supportTicket;
    }

    @Override
    public SupportTicket update(SupportTicket supportTicket) {
        String sql = "UPDATE support_tickets SET user_id = ?, assigned_to = ?, ticket_number = ?, subject = ?, description = ?, priority = ?, status = ? WHERE support_ticket_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, supportTicket.getUserId());
            stmt.setObject(2, supportTicket.getAssignedTo());
            stmt.setString(3, supportTicket.getTicketNumber());
            stmt.setString(4, supportTicket.getSubject());
            stmt.setString(5, supportTicket.getDescription());
            stmt.setString(6, supportTicket.getPriority());
            stmt.setString(7, supportTicket.getStatus());
            stmt.setInt(8, supportTicket.getSupportTicketId());

            stmt.executeUpdate();

            return findById(supportTicket.getSupportTicketId()).orElse(supportTicket);

        } catch (SQLException e) {
            logger.error("Error updating support ticket", e);
            return supportTicket;
        }
    }

    private SupportTicket extractSupportTicket(ResultSet rs) throws SQLException {
        SupportTicket ticket = new SupportTicket();
        ticket.setSupportTicketId(rs.getInt("support_ticket_id"));
        ticket.setUserId(rs.getInt("user_id"));
        ticket.setAssignedTo((Integer) rs.getObject("assigned_to"));
        ticket.setTicketNumber(rs.getString("ticket_number"));
        ticket.setSubject(rs.getString("subject"));
        ticket.setDescription(rs.getString("description"));
        ticket.setPriority(rs.getString("priority"));
        ticket.setStatus(rs.getString("status"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            ticket.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            ticket.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return ticket;
    }
}
