package com.example.festquestbackend.services;

import com.example.festquestbackend.models.users.FestUser;

import com.example.festquestbackend.repositories.users.FestUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FestUserService {

    private final FestUserRepository festUserRepository;
    private final PasswordEncoder passwordEncoder;

    public FestUserService(FestUserRepository festUserRepository, PasswordEncoder passwordEncoder) {
        this.festUserRepository = festUserRepository;
        this.passwordEncoder = passwordEncoder;
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
        festUser.setRole("ROLE_USER");
        festUserRepository.save(festUser);
    }

    public Long getLoggedInUser(FestUser festUser) {
         // todo find user id from session
        return festUserRepository.findById(festUser.getId()).get().getId();
    }
}
