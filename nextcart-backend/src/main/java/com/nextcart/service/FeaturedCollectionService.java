package com.nextcart.service;

import com.nextcart.model.FeaturedCollection;
import java.util.List;
import java.util.Optional;

public interface FeaturedCollectionService {
    Optional<FeaturedCollection> getCollectionById(Integer collectionId);
    Optional<FeaturedCollection> getCollectionBySlug(String slug);
    List<FeaturedCollection> getAllCollections();
    List<FeaturedCollection> getActiveCollections();
    FeaturedCollection createCollection(FeaturedCollection collection);
    FeaturedCollection updateCollection(FeaturedCollection collection);
    void deleteCollection(Integer collectionId);
}
