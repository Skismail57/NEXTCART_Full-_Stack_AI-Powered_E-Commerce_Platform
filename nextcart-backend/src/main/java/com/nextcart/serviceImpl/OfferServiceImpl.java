package com.nextcart.serviceImpl;

import com.nextcart.dao.OfferDAO;
import com.nextcart.daoImpl.OfferDAOImpl;
import com.nextcart.model.Offer;
import com.nextcart.service.OfferService;
import java.util.List;
import java.util.Optional;

public class OfferServiceImpl implements OfferService {
    private final OfferDAO offerDAO = new OfferDAOImpl();

    @Override
    public Optional<Offer> getOfferById(Integer id) {
        return offerDAO.findById(id);
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerDAO.findAll();
    }

    @Override
    public List<Offer> getActiveOffers() {
        return offerDAO.findActive();
    }

    @Override
    public Offer createOffer(Offer offer) {
        return offerDAO.create(offer);
    }

    @Override
    public Offer updateOffer(Offer offer) {
        return offerDAO.update(offer);
    }

    @Override
    public void deleteOffer(Integer id) {
        offerDAO.delete(id);
    }
}
