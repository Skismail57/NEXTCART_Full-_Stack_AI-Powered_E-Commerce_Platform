package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.CartDAO;
import com.nextcart.model.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class CartDAOImpl implements CartDAO {
    private static final Logger logger = LoggerFactory.getLogger(CartDAOImpl.class);

    @Override
    public Optional<Cart> findById(Integer cartId) {
        String sql = "SELECT * FROM cart WHERE cart_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractCart(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding cart by id: {}", cartId, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Cart> findByUserId(Integer userId) {
        String sql = "SELECT * FROM cart WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractCart(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding cart by user id: {}", userId, e);
        }
        return Optional.empty();
    }

    @Override
    public Cart create(Cart cart) {
        String sql = "INSERT INTO cart (user_id) VALUES (?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cart.getUserId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cart.setCartId(rs.getInt(1));
                return findById(cart.getCartId()).orElse(cart);
            }
        } catch (SQLException e) {
            logger.error("Error creating cart", e);
        }
        return cart;
    }

    @Override
    public Cart findOrCreateByUserId(Integer userId) {
        Optional<Cart> existingCart = findByUserId(userId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        return create(newCart);
    }

    private Cart extractCart(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setCartId(rs.getInt("cart_id"));
        cart.setUserId(rs.getInt("user_id"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            cart.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            cart.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return cart;
    }
}
