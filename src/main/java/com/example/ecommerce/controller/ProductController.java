package com.example.ecommerce.controller;
import com.example.ecommerce.model.Product; import com.example.ecommerce.repository.ProductRepository; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.*; import java.util.UUID;
@RestController @RequestMapping("/api/products") public class ProductController {
 private final ProductRepository repo; public ProductController(ProductRepository r){
  this.repo=r;
 }
 @GetMapping public List<Product> listActive(){
  return repo.findByActiveTrue();
 }
 @GetMapping("/all") @PreAuthorize("hasRole('ADMIN')") public List<Product> listAll(){
  return repo.findAll();
 }
 @PostMapping @PreAuthorize("hasRole('ADMIN')") public Product create(@RequestBody Product p){
  return repo.save(p);
 }
 @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public Product update(@PathVariable UUID id, @RequestBody Product p){
  p.setId(id); return repo.save(p);
 }
 @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public void delete(@PathVariable UUID id){
  repo.deleteById(id);
 }
}