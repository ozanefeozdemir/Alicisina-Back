package com.example.back2.service;

import com.example.back2.model.Product;
import com.example.back2.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByUserEmail(String email) {
        return productRepository.findByUserEmail(email);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedProduct.getName());
                    existing.setDescription(updatedProduct.getDescription());
                    existing.setPrice(updatedProduct.getPrice());
                    existing.setImageUrl(updatedProduct.getImageUrl());
                    existing.setUserEmail(updatedProduct.getUserEmail());
                    existing.setCategory(updatedProduct.getCategory());
                    existing.setSubCategory(updatedProduct.getSubCategory());
                    return productRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));
    }
}
