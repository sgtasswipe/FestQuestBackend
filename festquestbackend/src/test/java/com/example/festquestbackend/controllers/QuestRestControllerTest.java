package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import com.example.festquestbackend.services.QuestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Optional;


import java.time.LocalDateTime;

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
        // Arrange: Create a new Quest instance
        Quest quest = new Quest();
        quest.setTitle("Dragon Hunt");
        quest.setDescription("Defeat the mighty dragon!");
        quest.setImageUrl("https://imgur.com/gallery/daenerys-gets-taste-of-her-own-medicine-CdjyN");
        quest.setStartTime(LocalDateTime.now());
        quest.setEndTime(LocalDateTime.now().plusDays(100));

        // Act: Save the quest and retrieve it by ID
        Quest savedQuest = questRepository.save(quest); // Auto-generated ID
        Optional<Quest> foundQuest = questRepository.findById(savedQuest.getId());

        // Assert: Ensure the quest is found and fields match
        Assertions.assertTrue(foundQuest.isPresent());
        Assertions.assertEquals("Dragon Hunt", foundQuest.get().getTitle());
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
}