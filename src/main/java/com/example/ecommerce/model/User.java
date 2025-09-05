package com.example.ecommerce.model;
import jakarta.persistence.*; import lombok.*;
import java.time.OffsetDateTime; import java.util.UUID;
@Entity @Table(name="users") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
  @Id @GeneratedValue private UUID id;
  private String nit; private String name;
  private String surname;
  @Column(nullable=false, unique=true) private String email;
  private String phone;
  private String address;
  private OffsetDateTime createdAt;
  @ManyToOne(optional=false) private Role role;
  @Column(nullable=false) private String passwordHash;
  @PrePersist public void pre() {
    if(createdAt==null) createdAt=OffsetDateTime.now();
  }
}