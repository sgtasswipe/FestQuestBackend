package com.example.festquestbackend.services;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.models.users.User;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import com.example.festquestbackend.repositories.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }


    public User validateUserLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (!password.matches(user.getPassword()))
            throw new IllegalArgumentException("Invaild email or password");

        return user;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }
}
