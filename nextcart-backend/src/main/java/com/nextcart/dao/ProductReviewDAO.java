package com.nextcart.dao;

import com.nextcart.model.ProductReview;
import java.util.List;
import java.util.Optional;

public interface ProductReviewDAO {
    Optional<ProductReview> findById(Integer reviewId);
    List<ProductReview> findByProductId(Integer productId, int page, int size);
    List<ProductReview> findByUserId(Integer userId);
    List<ProductReview> findPendingApproval();
    ProductReview create(ProductReview review);
    ProductReview update(ProductReview review);
    boolean delete(Integer reviewId);
    double getAverageRating(Integer productId);
    int getReviewCount(Integer productId);
}
