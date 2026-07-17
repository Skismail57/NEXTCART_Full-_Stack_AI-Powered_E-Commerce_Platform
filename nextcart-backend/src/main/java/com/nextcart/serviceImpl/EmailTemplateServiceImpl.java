package com.nextcart.serviceImpl;

import com.nextcart.dao.EmailTemplateDAO;
import com.nextcart.daoImpl.EmailTemplateDAOImpl;
import com.nextcart.model.EmailTemplate;
import com.nextcart.service.EmailTemplateService;

import java.util.List;
import java.util.Optional;

public class EmailTemplateServiceImpl implements EmailTemplateService {
    private final EmailTemplateDAO emailTemplateDAO = new EmailTemplateDAOImpl();

    @Override
    public Optional<EmailTemplate> getEmailTemplateById(Integer id) {
        return emailTemplateDAO.findById(id);
    }

    @Override
    public Optional<EmailTemplate> getEmailTemplateBySlug(String slug) {
        return emailTemplateDAO.findBySlug(slug);
    }

    @Override
    public List<EmailTemplate> getAllEmailTemplates() {
        return emailTemplateDAO.findAll();
    }

    @Override
    public List<EmailTemplate> getActiveEmailTemplates() {
        return emailTemplateDAO.findActive();
    }

    @Override
    public EmailTemplate createEmailTemplate(EmailTemplate emailTemplate) {
        return emailTemplateDAO.create(emailTemplate);
    }

    @Override
    public EmailTemplate updateEmailTemplate(EmailTemplate emailTemplate) {
        return emailTemplateDAO.update(emailTemplate);
    }

    @Override
    public boolean deleteEmailTemplate(Integer id) {
        return emailTemplateDAO.delete(id);
    }
}
