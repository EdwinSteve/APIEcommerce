package com.example.ecommerce.controller;
import com.example.ecommerce.dto.OrderDtos;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.UUID;
@RestController @RequestMapping("/api/orders") public class OrderController {
 private final OrderService orders;
 private final UserRepository users;
 private final OrderRepository orderRepo;
 public OrderController(OrderService os, UserRepository ur, OrderRepository or){
  this.orders=os;
  this.users=ur;
  this.orderRepo=or;
 }
 @GetMapping("/mine") public List<Order> myOrders(@AuthenticationPrincipal UserDetails me){
  var u=users.findByEmail(me.getUsername()).orElseThrow();
  return orderRepo.findByUser(u);
 }
 @PostMapping("/cart/add") public Order addToCart(@AuthenticationPrincipal UserDetails me, @RequestBody OrderDtos.AddItem dto){
  var u=users.findByEmail(me.getUsername()).orElseThrow();
  return orders.addItem(u.getId(), dto.getProductId(), dto.getQuantity());
 }
 @GetMapping("/cart") public Order getCart(@AuthenticationPrincipal UserDetails me){
  var u=users.findByEmail(me.getUsername()).orElseThrow();
  return orders.getOrCreateCart(u.getId());
 }
 @PostMapping("/{orderId}/confirm") public Order confirm(@PathVariable UUID orderId){
  return orders.confirm(orderId);
 }
}