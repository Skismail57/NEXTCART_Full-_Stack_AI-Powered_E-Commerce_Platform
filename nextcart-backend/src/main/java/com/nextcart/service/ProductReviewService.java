package com.nextcart.service;

import com.nextcart.model.ProductReview;

import java.util.List;
import java.util.Optional;

public interface ProductReviewService {
    Optional<ProductReview> getReviewById(Integer id);
    List<ProductReview> getReviewsByProductId(Integer productId, int page, int size);
    List<ProductReview> getReviewsByUserId(Integer userId);
    List<ProductReview> getPendingReviews();
    ProductReview createReview(ProductReview review);
    ProductReview updateReview(ProductReview review);
    boolean deleteReview(Integer id);
    double getAverageRating(Integer productId);
    int getReviewCount(Integer productId);
}
