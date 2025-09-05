package com.example.ecommerce.security;
import com.example.ecommerce.service.AuthUserDetailsService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration @EnableMethodSecurity public class SecurityConfig {
 @Bean public SecurityFilterChain chain(HttpSecurity http, JwtAuthFilter jwt) throws Exception {
  http.csrf(cs->cs.disable()).sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/v3/api-docs/**","/swagger-ui/**","/actuator/health","/api/payments/webhook").permitAll().anyRequest().authenticated())
   .httpBasic(Customizer.withDefaults());
  http.addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
  return http.build(); }
 @Bean public PasswordEncoder encoder(){
  return new BCryptPasswordEncoder();
 }
 @Bean public AuthenticationManager authenticationManager(UserDetailsService uds, PasswordEncoder enc){
  var p=new DaoAuthenticationProvider();
  p.setUserDetailsService(uds);
  p.setPasswordEncoder(enc);
  return new ProviderManager(p);
 }
 @Bean public UserDetailsService uds(AuthUserDetailsService svc){
  return svc;
 }
}