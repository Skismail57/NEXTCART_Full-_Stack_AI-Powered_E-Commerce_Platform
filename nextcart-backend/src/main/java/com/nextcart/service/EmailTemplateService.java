package com.nextcart.service;

import com.nextcart.model.EmailTemplate;

import java.util.List;
import java.util.Optional;

public interface EmailTemplateService {
    Optional<EmailTemplate> getEmailTemplateById(Integer id);
    Optional<EmailTemplate> getEmailTemplateBySlug(String slug);
    List<EmailTemplate> getAllEmailTemplates();
    List<EmailTemplate> getActiveEmailTemplates();
    EmailTemplate createEmailTemplate(EmailTemplate emailTemplate);
    EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate);
    boolean deleteEmailTemplate(Integer id);
}
