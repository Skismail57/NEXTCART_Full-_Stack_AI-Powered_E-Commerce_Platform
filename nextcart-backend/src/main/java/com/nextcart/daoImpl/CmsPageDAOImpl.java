package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.CmsPageDAO;
import com.nextcart.model.CmsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CmsPageDAOImpl implements CmsPageDAO {
    private static final Logger logger = LoggerFactory.getLogger(CmsPageDAOImpl.class);

    @Override
    public Optional<CmsPage> findById(Integer pageId) {
        String sql = "SELECT * FROM cms_pages WHERE page_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pageId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractCmsPage(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding cms page by id: " + pageId, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<CmsPage> findBySlug(String slug) {
        String sql = "SELECT * FROM cms_pages WHERE slug = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, slug);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractCmsPage(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding cms page by slug: " + slug, e);
        }

        return Optional.empty();
    }

    @Override
    public List<CmsPage> findAll() {
        List<CmsPage> cmsPages = new ArrayList<>();
        String sql = "SELECT * FROM cms_pages ORDER BY title";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cmsPages.add(extractCmsPage(rs));
            }

        } catch (SQLException e) {
            logger.error("Error finding all cms pages", e);
        }

        return cmsPages;
    }

    @Override
    public CmsPage create(CmsPage cmsPage) {
        String sql = "INSERT INTO cms_pages (title, slug, content, status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cmsPage.getTitle());
            stmt.setString(2, cmsPage.getSlug());
            stmt.setString(3, cmsPage.getContent());
            stmt.setString(4, cmsPage.getStatus() != null ? cmsPage.getStatus() : "ACTIVE");
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cmsPage.setPageId(rs.getInt(1));
            }
            
            return findById(cmsPage.getPageId()).orElse(cmsPage);
        } catch (SQLException e) {
            logger.error("Error creating cms page", e);
            return cmsPage;
        }
    }

    @Override
    public CmsPage update(CmsPage cmsPage) {
        String sql = "UPDATE cms_pages SET title = ?, slug = ?, content = ?, status = ? WHERE page_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cmsPage.getTitle());
            stmt.setString(2, cmsPage.getSlug());
            stmt.setString(3, cmsPage.getContent());
            stmt.setString(4, cmsPage.getStatus());
            stmt.setInt(5, cmsPage.getPageId());
            
            stmt.executeUpdate();
            return findById(cmsPage.getPageId()).orElse(cmsPage);
        } catch (SQLException e) {
            logger.error("Error updating cms page", e);
            return cmsPage;
        }
    }

    @Override
    public void delete(Integer pageId) {
        String sql = "DELETE FROM cms_pages WHERE page_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, pageId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting cms page", e);
        }
    }

    private CmsPage extractCmsPage(ResultSet rs) throws SQLException {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageId(rs.getInt("page_id"));
        cmsPage.setTitle(rs.getString("title"));
        cmsPage.setSlug(rs.getString("slug"));
        cmsPage.setContent(rs.getString("content"));
        cmsPage.setStatus(rs.getString("status"));

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            cmsPage.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return cmsPage;
    }
}
