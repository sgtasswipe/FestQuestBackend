package com.example.festquestbackend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtUtil {

        private static final String SECRET_KEY = "mySecretKey";  // Use a more secure key in production
        private static final long EXPIRATION_TIME = 3600000;  // Token expiration (1 hour)

        public static String generateToken(Authentication authentication) {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            return Jwts.builder()
                    .setSubject(authentication.getName())
                    .claim("authorities", getAuthorities(authentication)) // Add user roles
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(key)
                    .compact();
        }

        private static String getAuthorities(Authentication authentication) {
            Set<String> roles = new HashSet<>();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                roles.add(authority.getAuthority());
            }
            return String.join(",", roles);
        }
    }

