package com.nextcart.cmsservice.service;

import com.nextcart.cmsservice.model.SystemSetting;

import java.util.List;
import java.util.Optional;

public interface SystemSettingService {
    List<SystemSetting> getAllSystemSettings();
    Optional<SystemSetting> getSystemSettingById(Integer id);
    Optional<SystemSetting> getSystemSettingByKey(String key);
    SystemSetting createSystemSetting(SystemSetting systemSetting);
    SystemSetting updateSystemSetting(Integer id, SystemSetting systemSetting);
    void deleteSystemSetting(Integer id);
}
