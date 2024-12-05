/*package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.repositories.users.FestUserRepository;
import com.example.festquestbackend.services.FestUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Optional;


@WebMvcTest(UserRestControllerTest.class)// Controller test.
class UserRestControllerTest {

//    private final UserService userService;
    //private final FestUserRepository userRepository;
    private final FestUserRepository userRepository;

    public UserRestControllerTest(FestUserService userService, FestUserRepository userRepository) {
//        this.userService = userService;
        this.userRepository = userRepository;
    }

    // Test HTTP 200, 404 etc
    @Test
    void testCreateAndFindById() {
        FestUser user = new FestUser();
        user.setFirstName("Dragonslayer");
        user.setLastName("TenThousand");
        user.setEmail("DragonSlayerOverTenThousand@Mailmail.cum");
        user.setPassword("BigDickClub69");

        FestUser savedUser = userRepository.save(user);
        Optional<FestUser> foundUser = userRepository.findById(savedUser.getId());

        // Actual tests.
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(savedUser.getId(), foundUser.get().getId());
    }

    @Test
    void ensureUsersNotEmptyInDatabase() {
//        assertEquals(userService.findAll()) > 0;
    }
}
    */