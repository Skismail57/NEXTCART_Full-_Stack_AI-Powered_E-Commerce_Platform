package com.nextcart.serviceImpl;

import com.nextcart.dao.SystemSettingDAO;
import com.nextcart.daoImpl.SystemSettingDAOImpl;
import com.nextcart.model.SystemSetting;
import com.nextcart.service.SystemSettingService;
import java.util.List;
import java.util.Optional;

public class SystemSettingServiceImpl implements SystemSettingService {
    private final SystemSettingDAO systemSettingDAO = new SystemSettingDAOImpl();

    @Override
    public Optional<SystemSetting> getSystemSettingById(Integer id) {
        return systemSettingDAO.findById(id);
    }

    @Override
    public Optional<SystemSetting> getSystemSettingByKey(String key) {
        return systemSettingDAO.findByKey(key);
    }

    @Override
    public List<SystemSetting> getAllSystemSettings() {
        return systemSettingDAO.findAll();
    }

    @Override
    public SystemSetting createSystemSetting(SystemSetting systemSetting) {
        return systemSettingDAO.create(systemSetting);
    }

    @Override
    public SystemSetting updateSystemSetting(SystemSetting systemSetting) {
        return systemSettingDAO.update(systemSetting);
    }

    @Override
    public SystemSetting saveOrUpdateSystemSetting(SystemSetting systemSetting) {
        return systemSettingDAO.saveOrUpdate(systemSetting);
    }

    @Override
    public void deleteSystemSetting(Integer id) {
        systemSettingDAO.delete(id);
    }
}
