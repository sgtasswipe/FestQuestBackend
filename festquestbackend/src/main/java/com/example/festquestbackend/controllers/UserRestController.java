package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.users.User;
import com.example.festquestbackend.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}") // TODO User or Users here?
    public ResponseEntity<User> getUser(@RequestParam long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

        @PostMapping("login")
        public ResponseEntity<String> logIn (@RequestBody User request, HttpSession session){
            User user = userService.validateUserLogin(request.getEmail(), request.getPassword());
            if (user != null) {
                session.setAttribute("userId", user.getId());
                return ResponseEntity.ok("login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials");
            }
        }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    }

