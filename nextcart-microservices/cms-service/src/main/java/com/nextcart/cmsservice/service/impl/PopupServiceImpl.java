package com.nextcart.cmsservice.service.impl;

import com.nextcart.cmsservice.model.Popup;
import com.nextcart.cmsservice.repository.PopupRepository;
import com.nextcart.cmsservice.service.PopupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PopupServiceImpl implements PopupService {
    private final PopupRepository popupRepository;

    @Override
    public List<Popup> getAllPopups() {
        return popupRepository.findAll();
    }

    @Override
    public Optional<Popup> getPopupById(Integer id) {
        return popupRepository.findById(id);
    }

    @Override
    public List<Popup> getActivePopups() {
        return popupRepository.findByStatus(Popup.PopupStatus.ACTIVE);
    }

    @Override
    public Popup createPopup(Popup popup) {
        return popupRepository.save(popup);
    }

    @Override
    public Popup updatePopup(Integer id, Popup popup) {
        return popupRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(popup.getTitle());
                    existing.setContent(popup.getContent());
                    existing.setImageUrl(popup.getImageUrl());
                    existing.setButtonText(popup.getButtonText());
                    existing.setButtonLink(popup.getButtonLink());
                    existing.setStatus(popup.getStatus());
                    return popupRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Popup not found"));
    }

    @Override
    public void deletePopup(Integer id) {
        popupRepository.deleteById(id);
    }
}
