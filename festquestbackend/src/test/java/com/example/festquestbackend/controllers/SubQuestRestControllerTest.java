package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.models.quests.SubQuest;
import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import com.example.festquestbackend.services.FestUserService;
import com.example.festquestbackend.util.JwtUtil;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SubQuestRestControllerTest {
    @Autowired
    SubQuestRestController subQuestRestController;

    @Autowired
    FestUserService festUserService;

    @Autowired
    QuestRepository questRepository;

    private String createJwtTokenForUser(FestUser festUser) {
        //userRestController.signup(festUser);
        JwtUtil jwtUtil = new JwtUtil();
        return jwtUtil.generateToken(festUser.getEmail());
    }

    @Test
    @Transactional
    public void testAuthorizedAccessToSubQuestByValidHierarchy() {
        FestUser festUser = festUserService.findById(7L).get();
        String jwt = createJwtTokenForUser(festUser);

        Quest quest = questRepository.findById(1L).get();

        // @Transactional keeps the session open, allowing lazy-loaded collections to be initialized
        Hibernate.initialize(quest.getQuestParticipants());

        ResponseEntity<SubQuest> result = subQuestRestController.getSubQuestById(1L, 1L, jwt);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @Transactional
    public void testUnauthorizedAccessToSubQuestByValidHierarchy() {
        FestUser festUser = festUserService.findById(7L).get();
        String jwt = createJwtTokenForUser(festUser);

        Quest quest = questRepository.findById(6L).get();

        Hibernate.initialize(quest.getQuestParticipants());

        // User shouldn't have access to SubQuest 4
        ResponseEntity<SubQuest> result = subQuestRestController.getSubQuestById(6L, 4L, jwt);

        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}
