package com.example.ecommerce.service;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service; import java.util.List;
@Service public class AuthUserDetailsService implements UserDetailsService {
 private final UserRepository repo; public AuthUserDetailsService(UserRepository r){
  this.repo=r;
 }
 @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
  User u=repo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Not found"));
  return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPasswordHash(), List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole().getName())));
 } }