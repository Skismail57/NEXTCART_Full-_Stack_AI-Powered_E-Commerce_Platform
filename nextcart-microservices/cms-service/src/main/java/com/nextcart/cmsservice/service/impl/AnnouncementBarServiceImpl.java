package com.nextcart.cmsservice.service.impl;

import com.nextcart.cmsservice.model.AnnouncementBar;
import com.nextcart.cmsservice.repository.AnnouncementBarRepository;
import com.nextcart.cmsservice.service.AnnouncementBarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementBarServiceImpl implements AnnouncementBarService {
    private final AnnouncementBarRepository announcementBarRepository;

    @Override
    public List<AnnouncementBar> getAllAnnouncementBars() {
        return announcementBarRepository.findAll();
    }

    @Override
    public Optional<AnnouncementBar> getAnnouncementBarById(Integer id) {
        return announcementBarRepository.findById(id);
    }

    @Override
    public List<AnnouncementBar> getActiveAnnouncementBars() {
        return announcementBarRepository.findByStatus(AnnouncementBar.AnnouncementStatus.ACTIVE);
    }

    @Override
    public AnnouncementBar createAnnouncementBar(AnnouncementBar announcementBar) {
        return announcementBarRepository.save(announcementBar);
    }

    @Override
    public AnnouncementBar updateAnnouncementBar(Integer id, AnnouncementBar announcementBar) {
        return announcementBarRepository.findById(id)
                .map(existing -> {
                    existing.setText(announcementBar.getText());
                    existing.setLink(announcementBar.getLink());
                    existing.setBackgroundColor(announcementBar.getBackgroundColor());
                    existing.setTextColor(announcementBar.getTextColor());
                    existing.setStatus(announcementBar.getStatus());
                    existing.setDisplayOrder(announcementBar.getDisplayOrder());
                    return announcementBarRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Announcement bar not found"));
    }

    @Override
    public void deleteAnnouncementBar(Integer id) {
        announcementBarRepository.deleteById(id);
    }
}
