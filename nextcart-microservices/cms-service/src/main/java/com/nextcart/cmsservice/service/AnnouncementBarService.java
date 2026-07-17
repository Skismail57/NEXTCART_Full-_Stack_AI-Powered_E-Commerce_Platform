package com.nextcart.cmsservice.service;

import com.nextcart.cmsservice.model.AnnouncementBar;

import java.util.List;
import java.util.Optional;

public interface AnnouncementBarService {
    List<AnnouncementBar> getAllAnnouncementBars();
    Optional<AnnouncementBar> getAnnouncementBarById(Integer id);
    List<AnnouncementBar> getActiveAnnouncementBars();
    AnnouncementBar createAnnouncementBar(AnnouncementBar announcementBar);
    AnnouncementBar updateAnnouncementBar(Integer id, AnnouncementBar announcementBar);
    void deleteAnnouncementBar(Integer id);
}
