package com.nextcart.dao;

import com.nextcart.model.AdminActivityLog;
import java.util.List;
import java.util.Optional;

public interface AdminActivityLogDAO {
    Optional<AdminActivityLog> findById(Integer logId);
    List<AdminActivityLog> findAll();
    List<AdminActivityLog> findByAdminId(Integer adminId);
    AdminActivityLog create(AdminActivityLog adminActivityLog);
    void delete(Integer logId);
}
