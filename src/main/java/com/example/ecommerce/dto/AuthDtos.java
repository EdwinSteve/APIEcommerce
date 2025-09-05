package com.example.ecommerce.dto;
import jakarta.validation.constraints.*; import lombok.Data;
@Data public class AuthDtos {
 @Data public static class Register {
  @NotBlank private String name;
  @NotBlank private String surname;
  @Email @NotBlank private String email;
  @NotBlank private String password;
  private String nit;
  private String phone;
  private String address;
 }
 @Data public static class Login {
  @Email @NotBlank private String email;
  @NotBlank private String password;
 }
 @Data public static class JwtResponse {
  private String token; private String role;
  public JwtResponse(String t,String r){
   this.token=t;this.role=r;}
 }
}