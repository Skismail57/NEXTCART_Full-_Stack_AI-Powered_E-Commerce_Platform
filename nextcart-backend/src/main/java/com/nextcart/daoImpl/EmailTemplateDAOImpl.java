package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.EmailTemplateDAO;
import com.nextcart.model.EmailTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmailTemplateDAOImpl implements EmailTemplateDAO {

    @Override
    public Optional<EmailTemplate> findById(Integer id) {
        String sql = "SELECT * FROM email_templates WHERE email_template_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractEmailTemplate(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<EmailTemplate> findBySlug(String slug) {
        String sql = "SELECT * FROM email_templates WHERE slug = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, slug);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractEmailTemplate(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<EmailTemplate> findAll() {
        List<EmailTemplate> templates = new ArrayList<>();
        String sql = "SELECT * FROM email_templates ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                templates.add(extractEmailTemplate(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return templates;
    }

    @Override
    public List<EmailTemplate> findActive() {
        List<EmailTemplate> templates = new ArrayList<>();
        String sql = "SELECT * FROM email_templates WHERE status = 'ACTIVE' ORDER BY created_at DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                templates.add(extractEmailTemplate(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return templates;
    }

    @Override
    public EmailTemplate create(EmailTemplate emailTemplate) {
        String sql = "INSERT INTO email_templates (name, slug, subject, content, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, emailTemplate.getName());
            pstmt.setString(2, emailTemplate.getSlug());
            pstmt.setString(3, emailTemplate.getSubject());
            pstmt.setString(4, emailTemplate.getContent());
            pstmt.setString(5, emailTemplate.getStatus());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    emailTemplate.setEmailTemplateId(rs.getInt(1));
                    return findById(emailTemplate.getEmailTemplateId()).orElse(emailTemplate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EmailTemplate update(EmailTemplate emailTemplate) {
        String sql = "UPDATE email_templates SET name = ?, slug = ?, subject = ?, content = ?, status = ? WHERE email_template_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emailTemplate.getName());
            pstmt.setString(2, emailTemplate.getSlug());
            pstmt.setString(3, emailTemplate.getSubject());
            pstmt.setString(4, emailTemplate.getContent());
            pstmt.setString(5, emailTemplate.getStatus());
            pstmt.setInt(6, emailTemplate.getEmailTemplateId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return findById(emailTemplate.getEmailTemplateId()).orElse(emailTemplate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM email_templates WHERE email_template_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private EmailTemplate extractEmailTemplate(ResultSet rs) throws SQLException {
        EmailTemplate template = new EmailTemplate();
        template.setEmailTemplateId(rs.getInt("email_template_id"));
        template.setName(rs.getString("name"));
        template.setSlug(rs.getString("slug"));
        template.setSubject(rs.getString("subject"));
        template.setContent(rs.getString("content"));
        template.setStatus(rs.getString("status"));
        template.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        template.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        return template;
    }
}
