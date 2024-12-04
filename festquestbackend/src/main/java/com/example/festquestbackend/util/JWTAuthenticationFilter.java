package com.example.festquestbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Remove "Bearer " prefix
            try {
                Claims claims = Jwts.parser()   // parser allows us to proccess the JWT token.
                        .setSigningKey(SecurityConstants.JWT_KEY)  // key must match with secret to insure it has not been tampered with
                        .parseClaimsJws(token)
                        .getBody();    // return a Claims object type.

                String username = claims.getSubject();
                Collection<GrantedAuthority> authorities = getAuthoritiesFromClaims(claims);   // method explain below

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);  // Set authentication in context
            } catch (Exception e) {
                logger.error("Invalid JWT token");
            }
        }
        filterChain.doFilter(request, response);  // Continue with the request processing
    }

    private Collection<GrantedAuthority> getAuthoritiesFromClaims(Claims claims) {
        String authorities = claims.get("authorities", String.class);
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());                       // will map roles to eg new SimpleGrantedAuthority("ROLE_ADMIN")
                                                                            //                       new SimpleGrantedAuthority("ROLE_USER")
                                                                            // from data in JWT token that might look like this
                                                                            // { "authorities": "ROLE_USER,ROLE_ADMIN"}
    }
}
