package com.nextcart.cmsservice.repository;

import com.nextcart.cmsservice.model.AnnouncementBar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnnouncementBarRepository extends JpaRepository<AnnouncementBar, Integer> {
    Optional<AnnouncementBar> findById(Integer id);
    List<AnnouncementBar> findByStatus(AnnouncementBar.AnnouncementStatus status);
}
