package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.users.User;
import com.example.festquestbackend.repositories.users.UserRepository;
import com.example.festquestbackend.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Optional;

@WebMvcTest
class UserRestControllerTest {

//    private final UserService userService;
    private final UserRepository userRepository;

    public UserRestControllerTest(UserService userService, UserRepository userRepository) {
//        this.userService = userService;
        this.userRepository = userRepository;
    }

    // Test HTTP 200, 404 etc
    @Test
    void testCreateAndFindById() {
        User user = new User();
        user.setFirstName("Dragonslayer");
        user.setLastName("TenThousand");
        user.setEmail("DragonSlayerOverTenThousand@Mailmail.cum");
        user.setPassword("BigDickClub69");

        User savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Actual tests.
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(savedUser.getId(), foundUser.get().getId());
    }

    @Test
    void ensureUsersNotEmptyInDatabase() {
//        assertEquals(userService.findAll()) > 0;
    }
}