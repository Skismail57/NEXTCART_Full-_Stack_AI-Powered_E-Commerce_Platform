package com.nextcart.dao;

import com.nextcart.model.AuditLog;

import java.util.List;
import java.util.Optional;

public interface AuditLogDAO {
    Optional<AuditLog> findById(Integer id);
    List<AuditLog> findAll();
    List<AuditLog> findByUserId(Integer userId);
    List<AuditLog> findByEntityType(String entityType);
    AuditLog create(AuditLog auditLog);
}
