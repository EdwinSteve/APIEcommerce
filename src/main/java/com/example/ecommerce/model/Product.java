package com.example.ecommerce.model;
import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal; import java.util.UUID;
@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue private UUID id;
    private String name;
@Column(length=2000) private String description;
@Column(precision=19, scale=2) private BigDecimal price; private Boolean active;
}
