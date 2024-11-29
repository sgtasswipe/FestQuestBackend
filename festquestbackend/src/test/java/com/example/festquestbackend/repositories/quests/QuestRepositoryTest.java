package com.example.festquestbackend.repositories.quests;

import com.example.festquestbackend.models.quests.Quest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Repository tests
class QuestRepositoryTest {
    
    // Integrationtest + To verify database queries, relationships, and entity mappings.

    @Autowired
    QuestRepository questRepository;

    @Test
    void testFindByName() {
    }

    @Test
    void testEnsureDatabaseIsPopulated() {
        List<Quest> questList = questRepository.findAll();

        assertNotNull(questList, "The quest list should not be null");
        assertFalse(questList.isEmpty(), "The quest list should not be empty");
    }

}