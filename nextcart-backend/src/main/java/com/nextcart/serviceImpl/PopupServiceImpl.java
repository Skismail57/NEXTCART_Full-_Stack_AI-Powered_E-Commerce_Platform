package com.nextcart.serviceImpl;

import com.nextcart.dao.PopupDAO;
import com.nextcart.daoImpl.PopupDAOImpl;
import com.nextcart.model.Popup;
import com.nextcart.service.PopupService;
import java.util.List;
import java.util.Optional;

public class PopupServiceImpl implements PopupService {
    private final PopupDAO popupDAO = new PopupDAOImpl();

    @Override
    public Optional<Popup> getPopupById(Integer popupId) {
        return popupDAO.findById(popupId);
    }

    @Override
    public List<Popup> getAllPopups() {
        return popupDAO.findAll();
    }

    @Override
    public List<Popup> getActivePopups() {
        return popupDAO.findActive();
    }

    @Override
    public Popup createPopup(Popup popup) {
        return popupDAO.create(popup);
    }

    @Override
    public Popup updatePopup(Popup popup) {
        return popupDAO.update(popup);
    }

    @Override
    public void deletePopup(Integer popupId) {
        popupDAO.delete(popupId);
    }
}
