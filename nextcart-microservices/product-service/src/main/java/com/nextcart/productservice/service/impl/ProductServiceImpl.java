package com.nextcart.productservice.service.impl;

import com.nextcart.productservice.model.Product;
import com.nextcart.productservice.repository.ProductRepository;
import com.nextcart.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> getProductBySlug(String slug) {
        return productRepository.findBySlug(slug);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer id, Product product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setSlug(product.getSlug());
                    existingProduct.setSku(product.getSku());
                    existingProduct.setShortDescription(product.getShortDescription());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setDiscountPrice(product.getDiscountPrice());
                    existingProduct.setTax(product.getTax());
                    existingProduct.setStock(product.getStock());
                    existingProduct.setWeight(product.getWeight());
                    existingProduct.setWarranty(product.getWarranty());
                    existingProduct.setStatus(product.getStatus());
                    existingProduct.setFeatured(product.getFeatured());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setSubCategory(product.getSubCategory());
                    existingProduct.setBrand(product.getBrand());
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
