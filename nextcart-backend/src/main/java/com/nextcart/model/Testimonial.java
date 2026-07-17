package com.nextcart.model;

import java.time.LocalDateTime;

public class Testimonial {
    private Integer testimonialId;
    private String customerName;
    private String customerAvatar;
    private Integer rating;
    private String text;
    private String status;
    private Integer displayOrder;
    private LocalDateTime createdAt;

    public Testimonial() {
    }

    public Integer getTestimonialId() {
        return testimonialId;
    }

    public void setTestimonialId(Integer testimonialId) {
        this.testimonialId = testimonialId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAvatar() {
        return customerAvatar;
    }

    public void setCustomerAvatar(String customerAvatar) {
        this.customerAvatar = customerAvatar;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
