package com.nextcart.dao;

import com.nextcart.model.FeaturedCollection;
import java.util.List;
import java.util.Optional;

public interface FeaturedCollectionDAO {
    Optional<FeaturedCollection> findById(Integer collectionId);
    Optional<FeaturedCollection> findBySlug(String slug);
    List<FeaturedCollection> findAll();
    List<FeaturedCollection> findActive();
    FeaturedCollection create(FeaturedCollection collection);
    FeaturedCollection update(FeaturedCollection collection);
    void delete(Integer collectionId);
}
