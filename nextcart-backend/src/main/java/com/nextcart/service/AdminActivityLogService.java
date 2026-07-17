package com.nextcart.service;

import com.nextcart.model.AdminActivityLog;
import java.util.List;
import java.util.Optional;

public interface AdminActivityLogService {
    Optional<AdminActivityLog> getAdminActivityLogById(Integer id);
    List<AdminActivityLog> getAllAdminActivityLogs();
    List<AdminActivityLog> getAdminActivityLogsByAdminId(Integer adminId);
    AdminActivityLog createAdminActivityLog(AdminActivityLog adminActivityLog);
    void deleteAdminActivityLog(Integer id);
}
