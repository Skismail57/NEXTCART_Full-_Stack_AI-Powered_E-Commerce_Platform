package com.nextcart.cmsservice.service.impl;

import com.nextcart.cmsservice.model.Testimonial;
import com.nextcart.cmsservice.repository.TestimonialRepository;
import com.nextcart.cmsservice.service.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {
    private final TestimonialRepository testimonialRepository;

    @Override
    public List<Testimonial> getAllTestimonials() {
        return testimonialRepository.findAll();
    }

    @Override
    public Optional<Testimonial> getTestimonialById(Integer id) {
        return testimonialRepository.findById(id);
    }

    @Override
    public List<Testimonial> getActiveTestimonials() {
        return testimonialRepository.findByStatus(Testimonial.TestimonialStatus.ACTIVE);
    }

    @Override
    public Testimonial createTestimonial(Testimonial testimonial) {
        return testimonialRepository.save(testimonial);
    }

    @Override
    public Testimonial updateTestimonial(Integer id, Testimonial testimonial) {
        return testimonialRepository.findById(id)
                .map(existing -> {
                    existing.setName(testimonial.getName());
                    existing.setRole(testimonial.getRole());
                    existing.setContent(testimonial.getContent());
                    existing.setImageUrl(testimonial.getImageUrl());
                    existing.setDisplayOrder(testimonial.getDisplayOrder());
                    existing.setStatus(testimonial.getStatus());
                    return testimonialRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Testimonial not found"));
    }

    @Override
    public void deleteTestimonial(Integer id) {
        testimonialRepository.deleteById(id);
    }
}
