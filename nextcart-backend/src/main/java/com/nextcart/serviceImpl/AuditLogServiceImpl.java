package com.nextcart.serviceImpl;

import com.nextcart.dao.AuditLogDAO;
import com.nextcart.daoImpl.AuditLogDAOImpl;
import com.nextcart.model.AuditLog;
import com.nextcart.service.AuditLogService;

import java.util.List;
import java.util.Optional;

public class AuditLogServiceImpl implements AuditLogService {
    private final AuditLogDAO auditLogDAO = new AuditLogDAOImpl();

    @Override
    public Optional<AuditLog> getAuditLogById(Integer id) {
        return auditLogDAO.findById(id);
    }

    @Override
    public List<AuditLog> getAllAuditLogs() {
        return auditLogDAO.findAll();
    }

    @Override
    public List<AuditLog> getAuditLogsByUserId(Integer userId) {
        return auditLogDAO.findByUserId(userId);
    }

    @Override
    public List<AuditLog> getAuditLogsByEntityType(String entityType) {
        return auditLogDAO.findByEntityType(entityType);
    }

    @Override
    public AuditLog createAuditLog(AuditLog auditLog) {
        return auditLogDAO.create(auditLog);
    }
}
