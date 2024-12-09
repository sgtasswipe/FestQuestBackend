package com.example.festquestbackend.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.services.FestUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private static final long EXPIRATION_TIME = 3600000;  // Token expiration (1 hour)
    private final FestUserService festUserService;

    public JwtUtil(FestUserService festUserService) {
        this.festUserService = festUserService;
    }

    // Generate JWT
    public String generateToken(String email) {
        FestUser festUser = festUserService.findByEmail(email);
        System.out.printf("JWT kaldt");

        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().setIssuer("Deez").setSubject(festUser.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 300000000))
                .signWith(key).compact();

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}