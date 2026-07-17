package com.nextcart.service;

import com.nextcart.model.Product;
import com.nextcart.model.ProductImage;
import com.nextcart.model.ProductVariant;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(Integer id);
    Optional<Product> getProductBySlug(String slug);
    List<Product> getAllProducts(int page, int size);
    List<Product> getFeaturedProducts(int limit);
    List<Product> getProductsByCategory(Integer categoryId, int page, int size);
    List<Product> searchProducts(String query, int page, int size);
    Product createProduct(Product product, List<ProductImage> productImages, List<ProductVariant> productVariants);
    Product updateProduct(Product product);
    void deleteProduct(Integer id);
}
