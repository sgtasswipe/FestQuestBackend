package com.example.festquestbackend.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import com.example.festquestbackend.services.QuestService;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuestRestControllerTest {
    @Autowired
    QuestService questService;
    @Autowired
    QuestRepository questRepository;

    @Test
    void testCreateAndFindById() {
        Quest quest = new Quest();
        quest.setTitle("Dragon Hunt");
        quest.setDescription("Defeat the mighty dragon!");
        quest.setImageUrl("https://imgur.com/gallery/daenerys-gets-taste-of-her-own-medicine-CdjyN");
        quest.setStartTime(LocalDateTime.now());
        quest.setEndTime(LocalDateTime.now().plusDays(100));

        Quest savedQuest = questRepository.save(quest);
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
}