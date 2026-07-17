package com.nextcart.dao;

import com.nextcart.model.Popup;
import java.util.List;
import java.util.Optional;

public interface PopupDAO {
    Optional<Popup> findById(Integer popupId);
    List<Popup> findAll();
    List<Popup> findActive();
    Popup create(Popup popup);
    Popup update(Popup popup);
    void delete(Integer popupId);
}
