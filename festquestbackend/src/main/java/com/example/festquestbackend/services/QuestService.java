package com.example.festquestbackend.services;

import com.example.festquestbackend.models.misc.ChangeLog;
import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.models.users.QuestParticipant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.models.quests.SubQuest;
import com.example.festquestbackend.models.users.QuestParticipant;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import com.example.festquestbackend.models.users.FestUser;

import jakarta.transaction.Transactional;

@Service
public class QuestService {

    private final QuestRepository questRepository;
    private final FestUserService festUserService;
    public QuestService(QuestRepository questRepository, FestUserService festUserService) {
        this.questRepository = questRepository;
        this.festUserService = festUserService;
    }

    public List<Quest> findAll() {
        return questRepository.findAll();
    }

    public List<Quest> findAllForUser(Long userId) {
        // First, get all quests where the user is a participant
        List<Quest> quests = questRepository.findDistinctByQuestParticipants_FestUserId(userId);

        if (quests.isEmpty()) {
            // If no quests found through participants, try getting all quests
            // This is temporary for testing - remove in production
            return questRepository.findAll();
        }

        return quests.stream()
            .map(quest -> {
                // Filter participants to only include the current user
                List<QuestParticipant> filteredParticipants = quest.getQuestParticipants()
                    .stream()
                    .filter(qp -> qp.getUser().getId() == userId)
                    .collect(Collectors.toList());
                quest.setQuestParticipants(filteredParticipants);
                return quest;
            })
            .collect(Collectors.toList());
    }

    public Optional<Quest> findById(long id) {
        return questRepository.findById(id);
    }


    public Optional<Quest> save(Quest quest) {
        try {
            validateQuestDates(quest);
            Quest savedQuest = questRepository.save(quest);
            return Optional.of(savedQuest);
        } catch (IllegalArgumentException e) {
            System.err.println("Error saving quest: " + e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            System.err.println("Error saving quest: " + e.getMessage());
            throw e; // Re-throw to be caught by controller
        }
    }

    public Quest createQuest(Quest quest, Long userId) {
        FestUser user = festUserService.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        QuestParticipant creator = new QuestParticipant();
        creator.setUser(user);
        creator.setQuest(quest);
        creator.setGoing(true);

        quest.setQuestParticipants(List.of(creator));
        Quest savedQuest = questRepository.save(quest);

        // Debug output
        System.out.println("Created quest with ID: " + savedQuest.getId());
        System.out.println("Number of participants: " + savedQuest.getQuestParticipants().size());

        return savedQuest;
    }

    public void validateQuestDates(Quest quest) {
        if (quest.getEndTime() != null && quest.getStartTime().isAfter(quest.getEndTime()))
            throw new IllegalArgumentException("End date must be before start date");


        if (quest.getStartTime().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Start time cannot be in the past");
    }

    public void updateQuest(Quest updatedQuest, Quest existingQuest) {
        try {
            validateQuestDates(updatedQuest);

            // Save the existing subQuestList
            List<SubQuest> existingSubQuests = existingQuest.getSubQuestList();

            // Copy properties excluding id and relationships
            BeanUtils.copyProperties(updatedQuest, existingQuest, "id", "subQuestList", "questParticipants");

            // Restore the subQuestList
            existingQuest.setSubQuestList(existingSubQuests);

            questRepository.save(existingQuest);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void deleteQuest(Quest quest) {
        try {
            // Clear relationships to ensure clean deletion
            quest.getQuestParticipants().clear();
            quest.getSubQuestList().clear();
            questRepository.delete(quest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete quest: " + e.getMessage());
        }
    }

}
