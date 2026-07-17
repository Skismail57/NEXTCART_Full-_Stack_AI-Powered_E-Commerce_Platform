package com.nextcart.dao;

import com.nextcart.model.AnnouncementBar;
import java.util.List;
import java.util.Optional;

public interface AnnouncementBarDAO {
    Optional<AnnouncementBar> findById(Integer announcementId);
    List<AnnouncementBar> findAll();
    List<AnnouncementBar> findActive();
    AnnouncementBar create(AnnouncementBar announcement);
    AnnouncementBar update(AnnouncementBar announcement);
    void delete(Integer announcementId);
}
