package com.nextcart.model;

import java.time.LocalDateTime;

public class FraudLog {
    private Integer fraudLogId;
    private Integer userId;
    private Integer orderId;
    private Integer riskScore;
    private String reason;
    private String actionTaken;
    private LocalDateTime createdAt;

    public FraudLog() {
    }

    public Integer getFraudLogId() {
        return fraudLogId;
    }

    public void setFraudLogId(Integer fraudLogId) {
        this.fraudLogId = fraudLogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
