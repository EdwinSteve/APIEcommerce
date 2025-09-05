package com.example.ecommerce.service;
import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service public class WompiService {
 @Value("${app.wompi.baseUrl}") private String baseUrl;
 @Value("${app.wompi.publicKey}") private String publicKey;
 @Value("${app.wompi.privateKey}") private String privateKey;
 private final RestTemplate rt=new RestTemplate();
 private final ObjectMapper mapper=new ObjectMapper();
 public JsonNode getMerchant(){
  String url=baseUrl+"/v1/merchants/"+publicKey; ResponseEntity<String> resp=rt.getForEntity(url,String.class);
  try{
   return mapper.readTree(resp.getBody());
  }catch(Exception e){
   throw new RuntimeException(e);
  }
 }
 public JsonNode createTransaction(String jsonBody){
  String url=baseUrl+"/v1/transactions";
  HttpHeaders h=new HttpHeaders();
  h.setContentType(MediaType.APPLICATION_JSON);
  h.setBearerAuth(privateKey);
  HttpEntity<String> entity=new HttpEntity<>(jsonBody,h);
  ResponseEntity<String> resp=rt.postForEntity(url,entity,String.class);
  try{
   return mapper.readTree(resp.getBody());
  }
  catch(Exception e){
   throw new RuntimeException(e);
  }
 }
}