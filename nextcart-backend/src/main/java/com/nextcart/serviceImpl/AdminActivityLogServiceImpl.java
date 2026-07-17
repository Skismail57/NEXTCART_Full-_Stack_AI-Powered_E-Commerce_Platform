package com.nextcart.serviceImpl;

import com.nextcart.dao.AdminActivityLogDAO;
import com.nextcart.daoImpl.AdminActivityLogDAOImpl;
import com.nextcart.model.AdminActivityLog;
import com.nextcart.service.AdminActivityLogService;
import java.util.List;
import java.util.Optional;

public class AdminActivityLogServiceImpl implements AdminActivityLogService {
    private final AdminActivityLogDAO adminActivityLogDAO = new AdminActivityLogDAOImpl();

    @Override
    public Optional<AdminActivityLog> getAdminActivityLogById(Integer id) {
        return adminActivityLogDAO.findById(id);
    }

    @Override
    public List<AdminActivityLog> getAllAdminActivityLogs() {
        return adminActivityLogDAO.findAll();
    }

    @Override
    public List<AdminActivityLog> getAdminActivityLogsByAdminId(Integer adminId) {
        return adminActivityLogDAO.findByAdminId(adminId);
    }

    @Override
    public AdminActivityLog createAdminActivityLog(AdminActivityLog adminActivityLog) {
        return adminActivityLogDAO.create(adminActivityLog);
    }

    @Override
    public void deleteAdminActivityLog(Integer id) {
        adminActivityLogDAO.delete(id);
    }
}
