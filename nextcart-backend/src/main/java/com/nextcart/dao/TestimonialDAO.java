package com.nextcart.dao;

import com.nextcart.model.Testimonial;
import java.util.List;
import java.util.Optional;

public interface TestimonialDAO {
    Optional<Testimonial> findById(Integer testimonialId);
    List<Testimonial> findAll();
    List<Testimonial> findActive();
    Testimonial create(Testimonial testimonial);
    Testimonial update(Testimonial testimonial);
    void delete(Integer testimonialId);
}
