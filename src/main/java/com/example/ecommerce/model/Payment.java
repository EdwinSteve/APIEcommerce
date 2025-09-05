package com.example.ecommerce.model;
import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal; import java.time.OffsetDateTime; import java.util.UUID;
@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
 @Id @GeneratedValue private UUID id;
 @Enumerated(EnumType.STRING) private PaymentStatus status;
 @Column(precision=19, scale=2) private BigDecimal amount; // COP
 private String externalRef;
 private OffsetDateTime approvedAt;
 private String receiptUrl;
 @Column(columnDefinition="TEXT") private String rawPayload;
 @ManyToOne(optional=false) private Order order;
}