package com.example.festquestbackend.services;

import com.example.festquestbackend.models.users.FestUser;

import com.example.festquestbackend.repositories.users.FestUserRepository;
import com.example.festquestbackend.util.JwtUtil;
import com.example.festquestbackend.util.PasswordEncoder;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FestUserService {
    private final FestUserRepository festUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public FestUserService(FestUserRepository festUserRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.festUserRepository = festUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public List<FestUser> findAll() {
        return festUserRepository.findAll();
    }

    public Optional<FestUser> findById(long id) {
        return festUserRepository.findById(id);
    }


    public FestUser validateUserLogin(String email, String password) {
        FestUser festUser = festUserRepository.findByEmail(email).get();
        if (!password.matches(festUser.getPassword()))
            throw new IllegalArgumentException("Invaild email or password");

        return festUser;
    }

    public Optional<FestUser> findByEmail(String email) {
        return festUserRepository.findByEmail(email);
    }

    public void createUser(FestUser festUser) {
        String hashedPwd = passwordEncoder.encode(festUser.getPassword());
        festUser.setPassword(hashedPwd);

        // Give a new user ROLE_USER by default (Spring security)
        // festUser.setRole("ROLE_USER");
        festUserRepository.save(festUser);
    }

    public FestUser getFestUserByAuthHeader(String authorizationHeader) {
        String email = jwtUtil.extractEmailFromAuthHeader(authorizationHeader);
        Optional<FestUser> festUser = findByEmail(email);
        return festUser.orElse(null);
    }
}
