package com.example.ecommerce.model;
import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal; import java.util.UUID;
@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {
 @Id @GeneratedValue private UUID id;
 private int quantity;
 @Column(precision=19, scale=2) private BigDecimal unitPrice;
 @Column(precision=19, scale=2) private BigDecimal subtotal;
 @ManyToOne(optional=false) private Product product;
 @ManyToOne(optional=false) private Order order;
}