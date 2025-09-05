package com.example.ecommerce.service;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service public class UserService {
 private final UserRepository users;
 private final RoleRepository roles;
 private final PasswordEncoder enc;
 public UserService(UserRepository u, RoleRepository r, PasswordEncoder e){
  this.users=u;this.roles=r;this.enc=e;
 }
 @Transactional public User registerUser(User u, String rawPwd){
  if(users.existsByEmail(u.getEmail())) throw new IllegalArgumentException("Email already in use");
  Role role=roles.findByName("USER");
  if(role==null) role=roles.save(Role.builder().name("USER").build()); u.setRole(role);
  u.setPasswordHash(enc.encode(rawPwd));
  return users.save(u);
 }
}