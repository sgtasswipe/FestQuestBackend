package com.example.festquestbackend.controllers;
/*
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import com.example.festquestbackend.services.QuestService;

@WebMvcTest(QuestRestController.class) // Changed from QuestRestControllerTest.class to QuestRestController.class
class QuestRestControllerTest {

    final QuestService questService;
    final QuestRepository questRepository;

//    To validate HTTP endpoints, request handling, and status codes. + integrati

    public QuestRestControllerTest(QuestService questService, QuestRepository questRepository){
        this.questService = questService;
        this.questRepository = questRepository;
    }

    @Test
    void testCreateAndFindById() {
        Quest quest = new Quest();
        quest.setTitle("Dragon Hunt");
        quest.setDescription("Defeat the mighty dragon!");
        quest.setImageUrl("https://imgur.com/gallery/daenerys-gets-taste-of-her-own-medicine-CdjyN");
        quest.setStartTime(LocalDateTime.now());
        quest.setEndTime(LocalDateTime.now().plusDays(100));

        Quest savedQuest = questRepository.save(quest); // Auto-generated ID
        Optional<Quest> foundQuest = questRepository.findById(savedQuest.getId());

        // Actual tests.
        Assertions.assertTrue(foundQuest.isPresent());
        Assertions.assertEquals(savedQuest.getId(), foundQuest.get().getId());
    }

    @Test
    void getQuestboard() {
    }

    @Test
    void getQuest() {
    }

    @Test
    void createQuest() {
    }

    @Test
    void updateQuest() {
    }

    @Test
    void deleteQuest() {
    }
}*/