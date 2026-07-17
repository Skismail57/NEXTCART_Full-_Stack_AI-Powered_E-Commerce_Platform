package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.ProductDAO;
import com.nextcart.model.Product;
import com.nextcart.model.ProductImage;
import com.nextcart.model.ProductVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImpl implements ProductDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProductDAOImpl.class);

    @Override
    public Optional<Product> findById(Integer productId) {
        String sql = "SELECT p.*, c.name as category_name, b.brand_name " +
                     "FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                     "WHERE p.product_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Product product = extractProduct(rs);
                product.setImages(findImagesByProductId(productId, conn));
                product.setVariants(findVariantsByProductId(productId, conn));
                return Optional.of(product);
            }

        } catch (SQLException e) {
            logger.error("Error finding product by id: " + productId, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Product> findBySlug(String slug) {
        String sql = "SELECT p.*, c.name as category_name, b.brand_name " +
                     "FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                     "WHERE p.slug = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, slug);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Product product = extractProduct(rs);
                product.setImages(findImagesByProductId(product.getProductId(), conn));
                product.setVariants(findVariantsByProductId(product.getProductId(), conn));
                return Optional.of(product);
            }

        } catch (SQLException e) {
            logger.error("Error finding product by slug: " + slug, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Product> findAll(int page, int size) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.name as category_name, b.brand_name " +
                     "FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                     "WHERE p.status = 'ACTIVE' " +
                     "ORDER BY p.created_at DESC " +
                     "LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, (page - 1) * size);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = extractProduct(rs);
                product.setImages(findImagesByProductId(product.getProductId(), conn));
                products.add(product);
            }

        } catch (SQLException e) {
            logger.error("Error finding all products", e);
        }

        return products;
    }

    @Override
    public List<Product> findFeatured(int limit) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.name as category_name, b.brand_name " +
                     "FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                     "WHERE p.status = 'ACTIVE' AND p.featured = TRUE " +
                     "ORDER BY p.created_at DESC " +
                     "LIMIT ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = extractProduct(rs);
                product.setImages(findImagesByProductId(product.getProductId(), conn));
                products.add(product);
            }

        } catch (SQLException e) {
            logger.error("Error finding featured products", e);
        }

        return products;
    }

    @Override
    public List<Product> findByCategory(Integer categoryId, int page, int size) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.name as category_name, b.brand_name " +
                     "FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                     "WHERE p.status = 'ACTIVE' AND p.category_id = ? " +
                     "ORDER BY p.created_at DESC " +
                     "LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            stmt.setInt(2, size);
            stmt.setInt(3, (page - 1) * size);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = extractProduct(rs);
                product.setImages(findImagesByProductId(product.getProductId(), conn));
                products.add(product);
            }

        } catch (SQLException e) {
            logger.error("Error finding products by category: " + categoryId, e);
        }

        return products;
    }

    @Override
    public List<Product> search(String query, int page, int size) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.name as category_name, b.brand_name " +
                     "FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.category_id " +
                     "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                     "WHERE p.status = 'ACTIVE' AND " +
                     "(p.product_name LIKE ? OR p.description LIKE ? OR p.short_description LIKE ?) " +
                     "ORDER BY p.created_at DESC " +
                     "LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + query + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setInt(4, size);
            stmt.setInt(5, (page - 1) * size);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = extractProduct(rs);
                product.setImages(findImagesByProductId(product.getProductId(), conn));
                products.add(product);
            }

        } catch (SQLException e) {
            logger.error("Error searching products: " + query, e);
        }

        return products;
    }

    @Override
    public Product create(Product product) {
        String sql = "INSERT INTO products (category_id, sub_category_id, brand_id, product_name, slug, sku, short_description, description, price, discount_price, tax, stock, weight, warranty, status, featured) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setObject(1, product.getCategoryId());
            stmt.setObject(2, product.getSubCategoryId());
            stmt.setObject(3, product.getBrandId());
            stmt.setString(4, product.getProductName());
            stmt.setString(5, product.getSlug());
            stmt.setString(6, product.getSku());
            stmt.setString(7, product.getShortDescription());
            stmt.setString(8, product.getDescription());
            stmt.setBigDecimal(9, product.getPrice());
            stmt.setBigDecimal(10, product.getDiscountPrice());
            stmt.setBigDecimal(11, product.getTax() != null ? product.getTax() : java.math.BigDecimal.ZERO);
            stmt.setInt(12, product.getStock() != null ? product.getStock() : 0);
            stmt.setBigDecimal(13, product.getWeight());
            stmt.setString(14, product.getWarranty());
            stmt.setString(15, product.getStatus() != null ? product.getStatus() : "ACTIVE");
            stmt.setBoolean(16, product.getFeatured() != null ? product.getFeatured() : false);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                product.setProductId(rs.getInt(1));
                return findById(product.getProductId()).orElse(product);
            }

        } catch (SQLException e) {
            logger.error("Error creating product", e);
        }

        return product;
    }

    @Override
    public Product update(Product product) {
        String sql = "UPDATE products SET category_id = ?, sub_category_id = ?, brand_id = ?, product_name = ?, slug = ?, sku = ?, short_description = ?, description = ?, price = ?, discount_price = ?, tax = ?, stock = ?, weight = ?, warranty = ?, status = ?, featured = ? WHERE product_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, product.getCategoryId());
            stmt.setObject(2, product.getSubCategoryId());
            stmt.setObject(3, product.getBrandId());
            stmt.setString(4, product.getProductName());
            stmt.setString(5, product.getSlug());
            stmt.setString(6, product.getSku());
            stmt.setString(7, product.getShortDescription());
            stmt.setString(8, product.getDescription());
            stmt.setBigDecimal(9, product.getPrice());
            stmt.setBigDecimal(10, product.getDiscountPrice());
            stmt.setBigDecimal(11, product.getTax() != null ? product.getTax() : java.math.BigDecimal.ZERO);
            stmt.setInt(12, product.getStock() != null ? product.getStock() : 0);
            stmt.setBigDecimal(13, product.getWeight());
            stmt.setString(14, product.getWarranty());
            stmt.setString(15, product.getStatus() != null ? product.getStatus() : "ACTIVE");
            stmt.setBoolean(16, product.getFeatured() != null ? product.getFeatured() : false);
            stmt.setInt(17, product.getProductId());

            stmt.executeUpdate();

            return findById(product.getProductId()).orElse(product);

        } catch (SQLException e) {
            logger.error("Error updating product", e);
            return product;
        }
    }

    @Override
    public void delete(Integer productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error deleting product", e);
        }
    }

    private List<ProductImage> findImagesByProductId(Integer productId, Connection conn) throws SQLException {
        List<ProductImage> images = new ArrayList<>();
        String sql = "SELECT * FROM product_images WHERE product_id = ? ORDER BY display_order, image_id";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                images.add(extractProductImage(rs));
            }
        }

        return images;
    }

    private List<ProductVariant> findVariantsByProductId(Integer productId, Connection conn) throws SQLException {
        List<ProductVariant> variants = new ArrayList<>();
        String sql = "SELECT * FROM product_variants WHERE product_id = ? ORDER BY variant_id";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                variants.add(extractProductVariant(rs));
            }
        }

        return variants;
    }

    private Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setSubCategoryId((Integer) rs.getObject("sub_category_id"));
        product.setBrandId((Integer) rs.getObject("brand_id"));
        product.setProductName(rs.getString("product_name"));
        product.setSlug(rs.getString("slug"));
        product.setSku(rs.getString("sku"));
        product.setShortDescription(rs.getString("short_description"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setDiscountPrice(rs.getBigDecimal("discount_price"));
        product.setTax(rs.getBigDecimal("tax"));
        product.setStock(rs.getInt("stock"));
        product.setWeight(rs.getBigDecimal("weight"));
        product.setWarranty(rs.getString("warranty"));
        product.setStatus(rs.getString("status"));
        product.setFeatured(rs.getBoolean("featured"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            product.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            product.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        product.setCategoryName(rs.getString("category_name"));
        product.setBrandName(rs.getString("brand_name"));
        return product;
    }

    private ProductImage extractProductImage(ResultSet rs) throws SQLException {
        ProductImage image = new ProductImage();
        image.setImageId(rs.getInt("image_id"));
        image.setProductId(rs.getInt("product_id"));
        image.setImageUrl(rs.getString("image_url"));
        image.setDisplayOrder(rs.getInt("display_order"));
        image.setIsPrimary(rs.getBoolean("is_primary"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            image.setCreatedAt(createdAt.toLocalDateTime());
        }

        return image;
    }

    private ProductVariant extractProductVariant(ResultSet rs) throws SQLException {
        ProductVariant variant = new ProductVariant();
        variant.setVariantId(rs.getInt("variant_id"));
        variant.setProductId(rs.getInt("product_id"));
        variant.setColor(rs.getString("color"));
        variant.setSize(rs.getString("size"));
        variant.setRam(rs.getString("ram"));
        variant.setStorage(rs.getString("storage"));
        variant.setPrice(rs.getBigDecimal("price"));
        variant.setStock(rs.getInt("stock"));
        variant.setSku(rs.getString("sku"));
        variant.setStatus(rs.getString("status"));
        return variant;
    }
}
