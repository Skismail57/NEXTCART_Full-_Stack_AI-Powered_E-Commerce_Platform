package com.nextcart.model;

import java.time.LocalDateTime;

public class TicketMessage {
    private Integer ticketMessageId;
    private Integer supportTicketId;
    private Integer userId;
    private String message;
    private LocalDateTime createdAt;

    public TicketMessage() {
    }

    public Integer getTicketMessageId() {
        return ticketMessageId;
    }

    public void setTicketMessageId(Integer ticketMessageId) {
        this.ticketMessageId = ticketMessageId;
    }

    public Integer getSupportTicketId() {
        return supportTicketId;
    }

    public void setSupportTicketId(Integer supportTicketId) {
        this.supportTicketId = supportTicketId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
