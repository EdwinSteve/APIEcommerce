package com.example.ecommerce.controller;
import com.example.ecommerce.dto.AuthDtos;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.JwtService;
import com.example.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/auth") public class AuthController {
 private final UserService userService;
 private final UserRepository users;
 private final AuthenticationManager authManager;
 private final JwtService jwt;
 public AuthController(UserService us, UserRepository ur, AuthenticationManager am, JwtService jwt){
  this.userService=us;
  this.users=ur;
  this.authManager=am;
  this.jwt=jwt;
 }
 @PostMapping("/register") public AuthDtos.JwtResponse register(@Valid @RequestBody AuthDtos.Register dto){
  User u=User.builder().name(dto.getName()).surname(dto.getSurname()).email(dto.getEmail()).nit(dto.getNit()).phone(dto.getPhone()).address(dto.getAddress()).build(); userService.registerUser(u, dto.getPassword()); String token=jwt.generateToken(u.getEmail(), Map.of("role","USER"));
  return new AuthDtos.JwtResponse(token,"USER");
 }
 @PostMapping("/login") public AuthDtos.JwtResponse login(@Valid @RequestBody AuthDtos.Login dto){
  Authentication auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())); SecurityContextHolder.getContext().setAuthentication(auth);
  var user=users.findByEmail(dto.getEmail()).orElseThrow();
  String token=jwt.generateToken(user.getEmail(), Map.of("role",user.getRole().getName()));
  return new AuthDtos.JwtResponse(token, user.getRole().getName());
 }
}