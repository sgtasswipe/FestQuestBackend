package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.repositories.users.FestUserRepository;
import com.example.festquestbackend.services.FestUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

@Profile("test")
@SpringBootTest
class UserRestControllerTest {
    @Autowired
    private FestUserService  userService;
    @Autowired
    private FestUserRepository userRepository;


    // Test HTTP 200, 404 etc
    @Test
    void testFindByEmail() {
        String email = "DragonSlayerOverTenThousand@Mailmail.cum";
        Optional<FestUser> foundUser = userService.findByEmail(email);

        // Actual tests.
        Assertions.assertTrue(foundUser.isPresent());
    }

    @Test
    void ensureUsersNotEmptyInDatabase() {
//        assertEquals(userService.findAll()) > 0;
    }
}
