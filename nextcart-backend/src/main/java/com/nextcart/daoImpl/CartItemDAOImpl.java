package com.nextcart.daoImpl;

import com.nextcart.config.DatabaseConfig;
import com.nextcart.dao.CartItemDAO;
import com.nextcart.dao.ProductDAO;
import com.nextcart.model.CartItem;
import com.nextcart.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartItemDAOImpl implements CartItemDAO {
    private static final Logger logger = LoggerFactory.getLogger(CartItemDAOImpl.class);
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public Optional<CartItem> findById(Integer cartItemId) {
        String sql = "SELECT * FROM cart_items WHERE cart_item_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartItemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CartItem cartItem = extractCartItem(rs);
                // Fetch product
                Optional<Product> productOpt = productDAO.findById(cartItem.getProductId());
                productOpt.ifPresent(cartItem::setProduct);
                return Optional.of(cartItem);
            }
        } catch (SQLException e) {
            logger.error("Error finding cart item by id: {}", cartItemId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<CartItem> findByCartId(Integer cartId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart_items WHERE cart_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CartItem cartItem = extractCartItem(rs);
                // Fetch product
                Optional<Product> productOpt = productDAO.findById(cartItem.getProductId());
                productOpt.ifPresent(cartItem::setProduct);
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            logger.error("Error finding cart items for cart id: {}", cartId, e);
        }
        return cartItems;
    }

    @Override
    public Optional<CartItem> findByCartIdAndProductIdAndVariantId(Integer cartId, Integer productId, Integer variantId) {
        String sql = "SELECT * FROM cart_items WHERE cart_id = ? AND product_id = ? AND variant_id " +
                     (variantId != null ? "= ?" : "IS NULL");
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            if (variantId != null) {
                stmt.setInt(3, variantId);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractCartItem(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding cart item", e);
        }
        return Optional.empty();
    }

    @Override
    public CartItem create(CartItem cartItem) {
        String sql = "INSERT INTO cart_items (cart_id, product_id, variant_id, quantity, price, total) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cartItem.getCartId());
            stmt.setInt(2, cartItem.getProductId());
            if (cartItem.getVariantId() != null) {
                stmt.setInt(3, cartItem.getVariantId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setInt(4, cartItem.getQuantity());
            stmt.setBigDecimal(5, cartItem.getPrice());
            stmt.setBigDecimal(6, cartItem.getTotal());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cartItem.setCartItemId(rs.getInt(1));
                return findById(cartItem.getCartItemId()).orElse(cartItem);
            }
        } catch (SQLException e) {
            logger.error("Error creating cart item", e);
        }
        return cartItem;
    }

    @Override
    public CartItem update(CartItem cartItem) {
        String sql = "UPDATE cart_items SET product_id = ?, variant_id = ?, quantity = ?, price = ?, total = ? WHERE cart_item_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartItem.getProductId());
            if (cartItem.getVariantId() != null) {
                stmt.setInt(2, cartItem.getVariantId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setInt(3, cartItem.getQuantity());
            stmt.setBigDecimal(4, cartItem.getPrice());
            stmt.setBigDecimal(5, cartItem.getTotal());
            stmt.setInt(6, cartItem.getCartItemId());
            stmt.executeUpdate();
            return findById(cartItem.getCartItemId()).orElse(cartItem);
        } catch (SQLException e) {
            logger.error("Error updating cart item: {}", cartItem.getCartItemId(), e);
            return cartItem;
        }
    }

    @Override
    public boolean delete(Integer cartItemId) {
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartItemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting cart item: {}", cartItemId, e);
            return false;
        }
    }

    @Override
    public boolean deleteByCartId(Integer cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting all cart items for cart: {}", cartId, e);
            return false;
        }
    }

    @Override
    public BigDecimal getCartTotal(Integer cartId) {
        String sql = "SELECT SUM(total) as total FROM cart_items WHERE cart_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total");
                return total != null ? total : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            logger.error("Error getting cart total for cart id: {}", cartId, e);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public int getCartItemCount(Integer cartId) {
        String sql = "SELECT COUNT(*) as count FROM cart_items WHERE cart_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("Error getting cart item count for cart id: {}", cartId, e);
        }
        return 0;
    }

    private CartItem extractCartItem(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(rs.getInt("cart_item_id"));
        cartItem.setCartId(rs.getInt("cart_id"));
        cartItem.setProductId(rs.getInt("product_id"));
        cartItem.setVariantId((Integer) rs.getObject("variant_id"));
        cartItem.setQuantity(rs.getInt("quantity"));
        cartItem.setPrice(rs.getBigDecimal("price"));
        cartItem.setTotal(rs.getBigDecimal("total"));
        return cartItem;
    }
}
