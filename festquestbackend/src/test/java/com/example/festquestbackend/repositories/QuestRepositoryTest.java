package com.example.festquestbackend.repositories;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest // Repository tests
class QuestRepositoryTest {

    // Integrationtest + To verify database queries, relationships, and entity mappings.

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

        Quest savedQuest = questRepository.save(quest); // Auto-generated ID
        Optional<Quest> foundQuest = questRepository.findById(savedQuest.getId());

        // Actual tests.
        Assertions.assertTrue(foundQuest.isPresent());
        Assertions.assertEquals(savedQuest.getId(), foundQuest.get().getId());
    }

    @Test
    void testFindAllQuests() {
        assertFalse(questRepository.findAll().isEmpty());
    }

    @Test
    void testEnsureDatabaseIsPopulated() {
        List<Quest> questList = questRepository.findAll();

        assertNotNull(questList, "The quest list should not be null");
        assertFalse(questList.isEmpty(), "The quest list should not be empty");
    }

}