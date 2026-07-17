package com.nextcart.service;

import com.nextcart.model.AnnouncementBar;
import java.util.List;
import java.util.Optional;

public interface AnnouncementBarService {
    Optional<AnnouncementBar> getAnnouncementById(Integer announcementId);
    List<AnnouncementBar> getAllAnnouncements();
    List<AnnouncementBar> getActiveAnnouncements();
    AnnouncementBar createAnnouncement(AnnouncementBar announcement);
    AnnouncementBar updateAnnouncement(AnnouncementBar announcement);
    void deleteAnnouncement(Integer announcementId);
}
