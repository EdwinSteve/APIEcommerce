package com.example.ecommerce.repository;
import com.example.ecommerce.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Payment findByExternalRef(String externalRef);
}