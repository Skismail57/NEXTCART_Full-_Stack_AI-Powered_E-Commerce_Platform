package com.nextcart.dao;

import com.nextcart.model.SystemSetting;
import java.util.List;
import java.util.Optional;

public interface SystemSettingDAO {
    Optional<SystemSetting> findById(Integer settingId);
    Optional<SystemSetting> findByKey(String settingKey);
    List<SystemSetting> findAll();
    SystemSetting create(SystemSetting systemSetting);
    SystemSetting update(SystemSetting systemSetting);
    void delete(Integer settingId);
    SystemSetting saveOrUpdate(SystemSetting systemSetting);
}
