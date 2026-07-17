package com.nextcart.cmsservice.service;

import com.nextcart.cmsservice.model.Testimonial;

import java.util.List;
import java.util.Optional;

public interface TestimonialService {
    List<Testimonial> getAllTestimonials();
    Optional<Testimonial> getTestimonialById(Integer id);
    List<Testimonial> getActiveTestimonials();
    Testimonial createTestimonial(Testimonial testimonial);
    Testimonial updateTestimonial(Integer id, Testimonial testimonial);
    void deleteTestimonial(Integer id);
}
