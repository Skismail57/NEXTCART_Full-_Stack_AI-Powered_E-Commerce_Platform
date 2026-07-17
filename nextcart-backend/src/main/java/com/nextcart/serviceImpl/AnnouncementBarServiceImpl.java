package com.nextcart.serviceImpl;

import com.nextcart.dao.AnnouncementBarDAO;
import com.nextcart.daoImpl.AnnouncementBarDAOImpl;
import com.nextcart.model.AnnouncementBar;
import com.nextcart.service.AnnouncementBarService;
import java.util.List;
import java.util.Optional;

public class AnnouncementBarServiceImpl implements AnnouncementBarService {
    private final AnnouncementBarDAO announcementDAO = new AnnouncementBarDAOImpl();

    @Override
    public Optional<AnnouncementBar> getAnnouncementById(Integer announcementId) {
        return announcementDAO.findById(announcementId);
    }

    @Override
    public List<AnnouncementBar> getAllAnnouncements() {
        return announcementDAO.findAll();
    }

    @Override
    public List<AnnouncementBar> getActiveAnnouncements() {
        return announcementDAO.findActive();
    }

    @Override
    public AnnouncementBar createAnnouncement(AnnouncementBar announcement) {
        return announcementDAO.create(announcement);
    }

    @Override
    public AnnouncementBar updateAnnouncement(AnnouncementBar announcement) {
        return announcementDAO.update(announcement);
    }

    @Override
    public void deleteAnnouncement(Integer announcementId) {
        announcementDAO.delete(announcementId);
    }
}
