package com.nextcart.serviceImpl;

import com.nextcart.dao.ProductDAO;
import com.nextcart.dao.ProductImageDAO;
import com.nextcart.dao.ProductVariantDAO;
import com.nextcart.daoImpl.ProductDAOImpl;
import com.nextcart.daoImpl.ProductImageDAOImpl;
import com.nextcart.daoImpl.ProductVariantDAOImpl;
import com.nextcart.model.Product;
import com.nextcart.model.ProductImage;
import com.nextcart.model.ProductVariant;
import com.nextcart.service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final ProductImageDAO productImageDAO = new ProductImageDAOImpl();
    private final ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();

    @Override
    public Optional<Product> getProductById(Integer id) {
        Optional<Product> product = productDAO.findById(id);
        if (product.isPresent()) {
            product.get().setImages(productImageDAO.findByProductId(id));
            product.get().setVariants(productVariantDAO.findByProductId(id));
        }
        return product;
    }

    @Override
    public Optional<Product> getProductBySlug(String slug) {
        Optional<Product> product = productDAO.findBySlug(slug);
        if (product.isPresent()) {
            product.get().setImages(productImageDAO.findByProductId(product.get().getProductId()));
            product.get().setVariants(productVariantDAO.findByProductId(product.get().getProductId()));
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts(int page, int size) {
        List<Product> products = productDAO.findAll(page, size);
        for (Product product : products) {
            product.setImages(productImageDAO.findByProductId(product.getProductId()));
            product.setVariants(productVariantDAO.findByProductId(product.getProductId()));
        }
        return products;
    }

    @Override
    public List<Product> getFeaturedProducts(int limit) {
        List<Product> products = productDAO.findFeatured(limit);
        for (Product product : products) {
            product.setImages(productImageDAO.findByProductId(product.getProductId()));
            product.setVariants(productVariantDAO.findByProductId(product.getProductId()));
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategory(Integer categoryId, int page, int size) {
        List<Product> products = productDAO.findByCategory(categoryId, page, size);
        for (Product product : products) {
            product.setImages(productImageDAO.findByProductId(product.getProductId()));
            product.setVariants(productVariantDAO.findByProductId(product.getProductId()));
        }
        return products;
    }

    @Override
    public List<Product> searchProducts(String query, int page, int size) {
        List<Product> products = productDAO.search(query, page, size);
        for (Product product : products) {
            product.setImages(productImageDAO.findByProductId(product.getProductId()));
            product.setVariants(productVariantDAO.findByProductId(product.getProductId()));
        }
        return products;
    }

    @Override
    public Product createProduct(Product product, List<ProductImage> productImages, List<ProductVariant> productVariants) {
        Product createdProduct = productDAO.create(product);
        
        if (productImages != null && !productImages.isEmpty()) {
            for (ProductImage image : productImages) {
                image.setProductId(createdProduct.getProductId());
                productImageDAO.create(image);
            }
            createdProduct.setImages(productImageDAO.findByProductId(createdProduct.getProductId()));
        }
        
        if (productVariants != null && !productVariants.isEmpty()) {
            for (ProductVariant variant : productVariants) {
                variant.setProductId(createdProduct.getProductId());
                productVariantDAO.create(variant);
            }
            createdProduct.setVariants(productVariantDAO.findByProductId(createdProduct.getProductId()));
        }
        
        return createdProduct;
    }

    @Override
    public Product updateProduct(Product product) {
        Product updatedProduct = productDAO.update(product);
        updatedProduct.setImages(productImageDAO.findByProductId(updatedProduct.getProductId()));
        updatedProduct.setVariants(productVariantDAO.findByProductId(updatedProduct.getProductId()));
        return updatedProduct;
    }

    @Override
    public void deleteProduct(Integer id) {
        productImageDAO.deleteByProductId(id);
        productVariantDAO.deleteByProductId(id);
        productDAO.delete(id);
    }
}
