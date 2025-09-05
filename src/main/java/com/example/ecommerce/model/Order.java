package com.example.ecommerce.model;
import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal; import java.time.OffsetDateTime; import java.util.*;
@Entity @Table(name="orders") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
 @Id @GeneratedValue private UUID id;
 private OffsetDateTime createdAt;
 @Enumerated(EnumType.STRING) private OrderStatus status;
 @Column(precision=19, scale=2) private BigDecimal total;
 @ManyToOne(optional=false) private User user;
 @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
 private List<OrderItem> items = new ArrayList<>();
 @PrePersist public void pre() {
  if(createdAt==null) createdAt=OffsetDateTime.now();
  if(status==null) status=OrderStatus.PENDING;
  if(total==null) total=BigDecimal.ZERO;
 }
}