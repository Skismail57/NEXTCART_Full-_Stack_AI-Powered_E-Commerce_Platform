package com.nextcart.service;

import com.nextcart.model.AuditLog;

import java.util.List;
import java.util.Optional;

public interface AuditLogService {
    Optional<AuditLog> getAuditLogById(Integer id);
    List<AuditLog> getAllAuditLogs();
    List<AuditLog> getAuditLogsByUserId(Integer userId);
    List<AuditLog> getAuditLogsByEntityType(String entityType);
    AuditLog createAuditLog(AuditLog auditLog);
}
