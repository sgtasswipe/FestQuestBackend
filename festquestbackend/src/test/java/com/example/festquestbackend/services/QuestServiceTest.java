package com.example.festquestbackend.services;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.repositories.quests.QuestRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@Component
class QuestServiceTest {
    @Autowired
    QuestService questService;
    @Autowired
    QuestRepository questRepository;

    // TESTS FOR DATE VALIDATION //
    @Test
    public void testCorrectDatesNotThrowsFromDb() {
        Quest quest = questService.findById(1).get();
        assertDoesNotThrow(() -> questService.validateQuestDates(quest));
    }

    @Test
    public void testCorrectDatesNotThrows() {
        Quest quest = new Quest();
        quest.setStartTime(LocalDateTime.now().plusDays(1));
        quest.setEndTime(LocalDateTime.now().plusDays(3));
        assertDoesNotThrow(() -> questService.validateQuestDates(quest));
    }

    @Test
    public void testIncorrectDatesThrowsException() {
        Quest quest = new Quest();
        quest.setEndTime(LocalDateTime.now().plusDays(1));
        quest.setStartTime(LocalDateTime.now().plusDays(2));
        assertThrows(IllegalArgumentException.class, () -> questService.validateQuestDates(quest));
    }
}
