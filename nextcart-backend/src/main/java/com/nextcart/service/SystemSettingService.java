package com.nextcart.service;

import com.nextcart.model.SystemSetting;
import java.util.List;
import java.util.Optional;

public interface SystemSettingService {
    Optional<SystemSetting> getSystemSettingById(Integer id);
    Optional<SystemSetting> getSystemSettingByKey(String key);
    List<SystemSetting> getAllSystemSettings();
    SystemSetting createSystemSetting(SystemSetting systemSetting);
    SystemSetting updateSystemSetting(SystemSetting systemSetting);
    SystemSetting saveOrUpdateSystemSetting(SystemSetting systemSetting);
    void deleteSystemSetting(Integer id);
}
