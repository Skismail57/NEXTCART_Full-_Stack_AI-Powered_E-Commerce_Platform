package com.nextcart.dao;

import com.nextcart.model.Offer;
import java.util.List;
import java.util.Optional;

public interface OfferDAO {
    Optional<Offer> findById(Integer offerId);
    List<Offer> findAll();
    List<Offer> findActive();
    Offer create(Offer offer);
    Offer update(Offer offer);
    void delete(Integer offerId);
}
