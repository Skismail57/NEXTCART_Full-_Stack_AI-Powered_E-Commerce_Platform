package com.nextcart.serviceImpl;

import com.nextcart.dao.TestimonialDAO;
import com.nextcart.daoImpl.TestimonialDAOImpl;
import com.nextcart.model.Testimonial;
import com.nextcart.service.TestimonialService;
import java.util.List;
import java.util.Optional;

public class TestimonialServiceImpl implements TestimonialService {
    private final TestimonialDAO testimonialDAO = new TestimonialDAOImpl();

    @Override
    public Optional<Testimonial> getTestimonialById(Integer testimonialId) {
        return testimonialDAO.findById(testimonialId);
    }

    @Override
    public List<Testimonial> getAllTestimonials() {
        return testimonialDAO.findAll();
    }

    @Override
    public List<Testimonial> getActiveTestimonials() {
        return testimonialDAO.findActive();
    }

    @Override
    public Testimonial createTestimonial(Testimonial testimonial) {
        return testimonialDAO.create(testimonial);
    }

    @Override
    public Testimonial updateTestimonial(Testimonial testimonial) {
        return testimonialDAO.update(testimonial);
    }

    @Override
    public void deleteTestimonial(Integer testimonialId) {
        testimonialDAO.delete(testimonialId);
    }
}
