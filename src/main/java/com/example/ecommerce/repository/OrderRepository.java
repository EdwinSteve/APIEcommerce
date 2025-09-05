package com.example.ecommerce.repository;
import com.example.ecommerce.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);
}