package com.nextcart.service;

import com.nextcart.model.Popup;
import java.util.List;
import java.util.Optional;

public interface PopupService {
    Optional<Popup> getPopupById(Integer popupId);
    List<Popup> getAllPopups();
    List<Popup> getActivePopups();
    Popup createPopup(Popup popup);
    Popup updatePopup(Popup popup);
    void deletePopup(Integer popupId);
}
