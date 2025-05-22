package com.example.back2.repository;

import com.example.back2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserEmail(String userEmail);
}
