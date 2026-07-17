package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.TicketMessageDAO;
import com.nextcart.model.TicketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketMessageDAOImpl implements TicketMessageDAO {
    private static final Logger logger = LoggerFactory.getLogger(TicketMessageDAOImpl.class);

    @Override
    public Optional<TicketMessage> findById(Integer id) {
        String sql = "SELECT * FROM ticket_messages WHERE ticket_message_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractTicketMessage(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding ticket message by id: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<TicketMessage> findByTicketId(Integer ticketId) {
        List<TicketMessage> messages = new ArrayList<>();
        String sql = "SELECT * FROM ticket_messages WHERE support_ticket_id = ? ORDER BY created_at";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticketId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messages.add(extractTicketMessage(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding ticket messages by ticket id: " + ticketId, e);
        }

        return messages;
    }

    @Override
    public TicketMessage create(TicketMessage ticketMessage) {
        String sql = "INSERT INTO ticket_messages (support_ticket_id, user_id, message) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ticketMessage.getSupportTicketId());
            stmt.setInt(2, ticketMessage.getUserId());
            stmt.setString(3, ticketMessage.getMessage());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                ticketMessage.setTicketMessageId(rs.getInt(1));
                return findById(ticketMessage.getTicketMessageId()).orElse(ticketMessage);
            }

        } catch (SQLException e) {
            logger.error("Error creating ticket message", e);
        }

        return ticketMessage;
    }

    private TicketMessage extractTicketMessage(ResultSet rs) throws SQLException {
        TicketMessage message = new TicketMessage();
        message.setTicketMessageId(rs.getInt("ticket_message_id"));
        message.setSupportTicketId(rs.getInt("support_ticket_id"));
        message.setUserId(rs.getInt("user_id"));
        message.setMessage(rs.getString("message"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            message.setCreatedAt(createdAt.toLocalDateTime());
        }

        return message;
    }
}
