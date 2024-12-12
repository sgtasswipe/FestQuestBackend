package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.services.FestUserService;
import com.example.festquestbackend.util.JwtUtil;
import com.example.festquestbackend.util.PasswordEncoder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500", "http://localhost:63342"})
public class UserRestController {
    private final FestUserService festUserService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserRestController(FestUserService festUserService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.festUserService = festUserService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user/{id}") // TODO User or Users here?
    public ResponseEntity<FestUser> getUser(@RequestParam long id) {
        return festUserService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String requestEmail = request.get("email");
        String requestPassword = request.get("password");

        FestUser festUser = festUserService.findByEmail(requestEmail).get();

        if (passwordEncoder.verify(requestPassword, festUser.getPassword())) {
            String token = jwtUtil.generateToken(festUser.getEmail());
            response.setHeader("Authorization", token);
            return ResponseEntity.ok("Login successful" + token);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@RequestBody FestUser festUser) {
        try {
            festUserService.createUser(festUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}

