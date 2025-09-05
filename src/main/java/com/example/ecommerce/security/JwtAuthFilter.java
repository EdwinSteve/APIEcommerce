package com.example.ecommerce.security;
import io.jsonwebtoken.Claims; import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.security.core.userdetails.*; import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter; import java.io.IOException;
@Component public class JwtAuthFilter extends OncePerRequestFilter {
 private final JwtService jwt; private final UserDetailsService uds;
 public JwtAuthFilter(JwtService j, UserDetailsService u) {
  this.jwt=j; this.uds=u;
 }
 @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws ServletException, IOException {
  String h=req.getHeader("Authorization"); if(h!=null && h.startsWith("Bearer ")){
   try{
    Claims c=jwt.parse(h.substring(7)).getBody(); UserDetails u=uds.loadUserByUsername(c.getSubject());
   var auth=new UsernamePasswordAuthenticationToken(u,null,u.getAuthorities()); auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req)); SecurityContextHolder.getContext().setAuthentication(auth);
   }
   catch(Exception ignored){

   } }
  fc.doFilter(req,res);
 } }