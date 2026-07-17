package com.nextcart.service;

import com.nextcart.model.Offer;
import java.util.List;
import java.util.Optional;

public interface OfferService {
    Optional<Offer> getOfferById(Integer id);
    List<Offer> getAllOffers();
    List<Offer> getActiveOffers();
    Offer createOffer(Offer offer);
    Offer updateOffer(Offer offer);
    void deleteOffer(Integer id);
}
