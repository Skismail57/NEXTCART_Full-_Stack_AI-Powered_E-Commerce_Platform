package com.nextcart.serviceImpl;

import com.nextcart.dao.*;
import com.nextcart.daoImpl.*;
import com.nextcart.model.*;
import com.nextcart.service.FeaturedCollectionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeaturedCollectionServiceImpl implements FeaturedCollectionService {
    private final FeaturedCollectionDAO collectionDAO = new FeaturedCollectionDAOImpl();
    private final FeaturedCollectionProductDAO collectionProductDAO = new FeaturedCollectionProductDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public Optional<FeaturedCollection> getCollectionById(Integer collectionId) {
        Optional<FeaturedCollection> collectionOpt = collectionDAO.findById(collectionId);
        if (collectionOpt.isPresent()) {
            populateCollectionProducts(collectionOpt.get());
        }
        return collectionOpt;
    }

    @Override
    public Optional<FeaturedCollection> getCollectionBySlug(String slug) {
        Optional<FeaturedCollection> collectionOpt = collectionDAO.findBySlug(slug);
        if (collectionOpt.isPresent()) {
            populateCollectionProducts(collectionOpt.get());
        }
        return collectionOpt;
    }

    @Override
    public List<FeaturedCollection> getAllCollections() {
        List<FeaturedCollection> collections = collectionDAO.findAll();
        for (FeaturedCollection collection : collections) {
            populateCollectionProducts(collection);
        }
        return collections;
    }

    @Override
    public List<FeaturedCollection> getActiveCollections() {
        List<FeaturedCollection> collections = collectionDAO.findActive();
        for (FeaturedCollection collection : collections) {
            populateCollectionProducts(collection);
        }
        return collections;
    }

    @Override
    public FeaturedCollection createCollection(FeaturedCollection collection) {
        return collectionDAO.create(collection);
    }

    @Override
    public FeaturedCollection updateCollection(FeaturedCollection collection) {
        return collectionDAO.update(collection);
    }

    @Override
    public void deleteCollection(Integer collectionId) {
        collectionProductDAO.deleteByCollectionId(collectionId);
        collectionDAO.delete(collectionId);
    }

    private void populateCollectionProducts(FeaturedCollection collection) {
        List<FeaturedCollectionProduct> fcpList = collectionProductDAO.findByCollectionId(collection.getCollectionId());
        List<Product> products = new ArrayList<>();
        for (FeaturedCollectionProduct fcp : fcpList) {
            Optional<Product> productOpt = productDAO.findById(fcp.getProductId());
            productOpt.ifPresent(products::add);
        }
        collection.setProducts(products);
    }
}
