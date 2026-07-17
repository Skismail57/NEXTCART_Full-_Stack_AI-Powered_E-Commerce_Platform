package com.nextcart.cmsservice.service.impl;

import com.nextcart.cmsservice.model.SystemSetting;
import com.nextcart.cmsservice.repository.SystemSettingRepository;
import com.nextcart.cmsservice.service.SystemSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SystemSettingServiceImpl implements SystemSettingService {
    private final SystemSettingRepository systemSettingRepository;

    @Override
    public List<SystemSetting> getAllSystemSettings() {
        return systemSettingRepository.findAll();
    }

    @Override
    public Optional<SystemSetting> getSystemSettingById(Integer id) {
        return systemSettingRepository.findById(id);
    }

    @Override
    public Optional<SystemSetting> getSystemSettingByKey(String key) {
        return systemSettingRepository.findBySettingKey(key);
    }

    @Override
    public SystemSetting createSystemSetting(SystemSetting systemSetting) {
        return systemSettingRepository.save(systemSetting);
    }

    @Override
    public SystemSetting updateSystemSetting(Integer id, SystemSetting systemSetting) {
        return systemSettingRepository.findById(id)
                .map(existing -> {
                    existing.setSettingKey(systemSetting.getSettingKey());
                    existing.setSettingValue(systemSetting.getSettingValue());
                    return systemSettingRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("System setting not found"));
    }

    @Override
    public void deleteSystemSetting(Integer id) {
        systemSettingRepository.deleteById(id);
    }
}
