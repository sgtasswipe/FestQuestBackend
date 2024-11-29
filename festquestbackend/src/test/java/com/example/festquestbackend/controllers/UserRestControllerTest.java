package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.users.User;
import com.example.festquestbackend.services.UserService;
import org.junit.jupiter.api.Test;

class UserRestControllerTest {

    private final UserService userService;

    public UserRestControllerTest(UserService userService) {
        this.userService = userService;
    }

    // Test HTTP 200, 404 etc
    @Test
    void testCreateAndFindById() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        userService.save(user);


    }

    @Test
    void ensureUsersInDatabase() {
//        assertEquals(userService.findAll()) > 0;
    }
}