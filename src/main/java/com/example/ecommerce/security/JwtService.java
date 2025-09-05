package com.example.ecommerce.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
@Service public class JwtService {
 @Value("${app.jwt.secret}") private String jwtSecret; @Value("${app.jwt.expirationMs}") private long exp;
 private Key key() {
  return Keys.hmacShaKeyFor(Decoders.BASE64.decode(java.util.Base64.getEncoder().encodeToString(jwtSecret.getBytes())));
 }
 public String generateToken(String sub, Map<String,Object> claims) {
  Date now=new Date();
  return Jwts.builder().setSubject(sub).setIssuedAt(now).setExpiration(new Date(now.getTime()+exp)).addClaims(claims).signWith(key(), SignatureAlgorithm.HS256).compact(); }
 public Jws<Claims> parse(String t) {
  return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(t);
 }
}