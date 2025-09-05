package com.example.ecommerce.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.ecommerce.dto.PaymentDtos;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.WompiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
@RestController @RequestMapping("/api/payments") public class PaymentController {
 private final WompiService wompi;
 private final OrderRepository orders;
 private final PaymentRepository payments;
 private final ObjectMapper mapper=new ObjectMapper();
 @Value("${app.wompi.publicKey}") private String publicKey;
 public PaymentController(WompiService w, OrderRepository or, PaymentRepository pr){
  this.wompi=w; this.orders=or; this.payments=pr;
 }
 @GetMapping("/acceptance-token") public Object acceptanceToken(){
  return wompi.getMerchant();
 }
 @PostMapping("/orders/{orderId}/pay") public Object pay(@PathVariable UUID orderId, @RequestBody PaymentDtos.PayRequest req) throws Exception {
  var order=orders.findById(orderId).orElseThrow();
  if(order.getStatus()!=OrderStatus.PENDING) throw new IllegalStateException("Order not pending");
  long cents=order.getTotal().multiply(new BigDecimal("100")).longValueExact();
  var body=Map.of("amount_in_cents",cents,"currency","COP","customer_email",req.getCustomerEmail(),"reference","ORD-"+order.getId(),"acceptance_token",req.getAcceptanceToken(),"payment_method",Map.of("type","CARD","token","REPLACE_WITH_CARD_TOKEN"),"session_id", req.getSessionId()==null? "na":req.getSessionId());
  var resp=wompi.createTransaction(mapper.writeValueAsString(body));
  var p=Payment.builder().order(order).status(PaymentStatus.INITIATED).amount(order.getTotal()).externalRef(resp.path("data").path("id").asText(null)).rawPayload(resp.toString()).build(); payments.save(p); return resp; }
 @PostMapping("/webhook") public ResponseEntity<String> webhook(@RequestBody Map<String,Object> payload){
  try{
   var data=(Map<String,Object>)payload.get("data");
   var tx=(Map<String,Object>)data.get("transaction");
   String id=(String)tx.get("id"); String status=(String)tx.get("status");
   Payment p=payments.findByExternalRef(id);
   if(p!=null){
    switch(status){
     case "APPROVED" -> { p.setStatus(PaymentStatus.APPROVED);
      p.setApprovedAt(OffsetDateTime.now()); p.getOrder().setStatus(OrderStatus.PAID);
     }
     case "DECLINED" -> p.setStatus(PaymentStatus.DECLINED);
     case "REFUNDED" -> p.setStatus(PaymentStatus.REFUNDED); }
    p.setRawPayload(new ObjectMapper().valueToTree(payload).toString()); payments.save(p); orders.save(p.getOrder());
   }
   return ResponseEntity.ok("ok"); }
  catch(Exception ex){
   return ResponseEntity.ok("ignored");
  }
 } }