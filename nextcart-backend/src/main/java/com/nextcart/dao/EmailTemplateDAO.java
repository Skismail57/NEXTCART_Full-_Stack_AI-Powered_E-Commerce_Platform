package com.nextcart.dao;

import com.nextcart.model.EmailTemplate;

import java.util.List;
import java.util.Optional;

public interface EmailTemplateDAO {
    Optional<EmailTemplate> findById(Integer id);
    Optional<EmailTemplate> findBySlug(String slug);
    List<EmailTemplate> findAll();
    List<EmailTemplate> findActive();
    EmailTemplate create(EmailTemplate emailTemplate);
    EmailTemplate update(EmailTemplate emailTemplate);
    boolean delete(Integer id);
}
