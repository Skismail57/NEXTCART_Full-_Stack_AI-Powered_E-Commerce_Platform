package com.nextcart.dao;

import com.nextcart.model.FeaturedCollectionProduct;
import java.util.List;

public interface FeaturedCollectionProductDAO {
    List<FeaturedCollectionProduct> findByCollectionId(Integer collectionId);
    FeaturedCollectionProduct create(FeaturedCollectionProduct fcp);
    void deleteByCollectionId(Integer collectionId);
}
