package com.nextcart.cmsservice.service;

import com.nextcart.cmsservice.model.Popup;

import java.util.List;
import java.util.Optional;

public interface PopupService {
    List<Popup> getAllPopups();
    Optional<Popup> getPopupById(Integer id);
    List<Popup> getActivePopups();
    Popup createPopup(Popup popup);
    Popup updatePopup(Integer id, Popup popup);
    void deletePopup(Integer id);
}
