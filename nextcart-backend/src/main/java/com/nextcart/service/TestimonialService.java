package com.nextcart.service;

import com.nextcart.model.Testimonial;
import java.util.List;
import java.util.Optional;

public interface TestimonialService {
    Optional<Testimonial> getTestimonialById(Integer testimonialId);
    List<Testimonial> getAllTestimonials();
    List<Testimonial> getActiveTestimonials();
    Testimonial createTestimonial(Testimonial testimonial);
    Testimonial updateTestimonial(Testimonial testimonial);
    void deleteTestimonial(Integer testimonialId);
}
