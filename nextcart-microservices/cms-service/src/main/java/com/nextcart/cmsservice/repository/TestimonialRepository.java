package com.nextcart.cmsservice.repository;

import com.nextcart.cmsservice.model.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestimonialRepository extends JpaRepository<Testimonial, Integer> {
    Optional<Testimonial> findById(Integer id);
    List<Testimonial> findByStatus(Testimonial.TestimonialStatus status);
}
