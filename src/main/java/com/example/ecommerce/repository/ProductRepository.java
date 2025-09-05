package com.example.ecommerce.repository;
import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByActiveTrue();
}