package com.example.ecommerce.service;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;
@Service public class OrderService {
 private final OrderRepository orders;
 private final ProductRepository products;
 private final UserRepository users;
 public OrderService(OrderRepository o, ProductRepository p, UserRepository u){
  this.orders=o;
  this.products=p;
  this.users=u;
 }
 @Transactional public Order getOrCreateCart(UUID userId){
  var user=users.findById(userId).orElseThrow();
  return orders.findByUser(user).stream().filter(o->o.getStatus()==OrderStatus.PENDING).findFirst().orElseGet(()->orders.save(Order.builder().user(user).status(OrderStatus.PENDING).build()));
 }
 @Transactional public Order addItem(UUID userId, UUID productId, int qty){ if(qty<=0) throw new IllegalArgumentException("qty > 0");
  var order=getOrCreateCart(userId);
  var product=products.findById(productId).orElseThrow();
  var item=new OrderItem(); item.setOrder(order);
  item.setProduct(product); item.setQuantity(qty);
  item.setUnitPrice(product.getPrice()); item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(qty))); order.getItems().add(item);
  recalc(order);
  return orders.save(order);}
 @Transactional public Order removeItem(UUID orderId, UUID itemId){ var order=orders.findById(orderId).orElseThrow(); order.getItems().removeIf(i->i.getId().equals(itemId)); recalc(order);
  return orders.save(order);}
 @Transactional public Order confirm(UUID orderId){
  var order=orders.findById(orderId).orElseThrow();
  if(order.getItems().isEmpty()) throw new IllegalStateException("Cart is empty"); recalc(order);
  return order;
 }
 private void recalc(Order order){
  var total=order.getItems().stream().map(OrderItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
  order.setTotal(total);
 }
}