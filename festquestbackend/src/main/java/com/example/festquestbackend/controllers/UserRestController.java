package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.services.FestUserService;
import com.example.festquestbackend.util.JWTTokenGeneratorFilter;
import com.example.festquestbackend.util.JwtUtil;
import com.example.festquestbackend.util.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@CrossOrigin(origins = "*")

@RestController
public class UserRestController {
    private final FestUserService festUserService;
   private final PasswordEncoder passEncoder;
   private final AuthenticationManager authenticationManager;

    public UserRestController(FestUserService festUserService, PasswordEncoder passEncoder, AuthenticationManager authenticationManager) {
        this.festUserService = festUserService;
        this.passEncoder = passEncoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/user/{id}") // TODO User or Users here?
    public ResponseEntity<FestUser> getUser(@RequestParam long id) {
        return festUserService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    public String jwtGenerator (String email) {

        FestUser festUser = festUserService.findByEmail(email);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.printf("JWT kaldt");
        if (null != authentication) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            return Jwts.builder().setIssuer("Deez").setSubject("JWT Token")
                    .claim("username", festUser.getEmail())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + 300000000))
                    .signWith(key).compact();
        }
        return null;
    }
    @PostMapping("/dologin")
    public ResponseEntity<String> doLogin(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        if (authentication.isAuthenticated()) {
           //FestUser festUser = festUserService.findByEmail(email);
            return ResponseEntity.ok(jwtGenerator(email));
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

/*
    @PostMapping("/dologin")
    public ResponseEntity<String> logIn(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String hashpass = passEncoder.dencode(password);

        try {
            FestUser festUser = festUserService.validateUserLogin(email, hashpass);
            String token = JwtUtil.generateToken((Authentication) festUser);  // Generate JWT token using the user details
            return ResponseEntity.ok("Bearer " + token);  // Return the token to the frontend
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials");
        }
    }*/

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(@RequestBody FestUser festUser) {
        try {
            System.out.println("recevied data" + festUser);
            festUserService.createUser(festUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}

