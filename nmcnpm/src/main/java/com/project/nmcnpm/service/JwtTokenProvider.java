package com.project.nmcnpm.service;

import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;
    public Key signingKey;
    public JwtTokenProvider(@Value("${app.jwtSecret}") String jwtSecret,
                            @Value("${app.jwtExpirationInMs}") long jwtExpirationInMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationInMs = jwtExpirationInMs;
        try {
            byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
            SignatureAlgorithm.HS512.assertValidSigningKey(Keys.hmacShaKeyFor(keyBytes));
            this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            System.err.println("Provided JWT secret is weak or invalid for HS512. Generating a new secure key. " + e.getMessage());
            this.signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        }
    }
    public String generateToken(Long userId, String username, String userRole) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);
        claims.put("userRole", userRole);
        return Jwts.builder()
                .setClaims(claims) 
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }
    public String getUsernameFromJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            System.err.println("JWT validation failed: " + ex.getMessage());
        }
        return false;
    }
    public Key getSigningKey() {
        return this.signingKey;
    }
}