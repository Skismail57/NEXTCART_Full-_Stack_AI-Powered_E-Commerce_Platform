package com.nextcart.cmsservice.repository;

import com.nextcart.cmsservice.model.Popup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PopupRepository extends JpaRepository<Popup, Integer> {
    Optional<Popup> findById(Integer id);
    List<Popup> findByStatus(Popup.PopupStatus status);
}
