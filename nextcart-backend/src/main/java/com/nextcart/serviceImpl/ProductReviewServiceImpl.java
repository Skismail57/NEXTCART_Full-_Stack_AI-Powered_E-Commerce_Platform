package com.nextcart.serviceImpl;

import com.nextcart.dao.ProductReviewDAO;
import com.nextcart.daoImpl.ProductReviewDAOImpl;
import com.nextcart.model.ProductReview;
import com.nextcart.service.ProductReviewService;

import java.util.List;
import java.util.Optional;

public class ProductReviewServiceImpl implements ProductReviewService {
    private final ProductReviewDAO productReviewDAO = new ProductReviewDAOImpl();

    @Override
    public Optional<ProductReview> getReviewById(Integer id) {
        return productReviewDAO.findById(id);
    }

    @Override
    public List<ProductReview> getReviewsByProductId(Integer productId, int page, int size) {
        return productReviewDAO.findByProductId(productId, page, size);
    }

    @Override
    public List<ProductReview> getReviewsByUserId(Integer userId) {
        return productReviewDAO.findByUserId(userId);
    }

    @Override
    public List<ProductReview> getPendingReviews() {
        return productReviewDAO.findPendingApproval();
    }

    @Override
    public ProductReview createReview(ProductReview review) {
        return productReviewDAO.create(review);
    }

    @Override
    public ProductReview updateReview(ProductReview review) {
        return productReviewDAO.update(review);
    }

    @Override
    public boolean deleteReview(Integer id) {
        return productReviewDAO.delete(id);
    }

    @Override
    public double getAverageRating(Integer productId) {
        return productReviewDAO.getAverageRating(productId);
    }

    @Override
    public int getReviewCount(Integer productId) {
        return productReviewDAO.getReviewCount(productId);
    }
}
