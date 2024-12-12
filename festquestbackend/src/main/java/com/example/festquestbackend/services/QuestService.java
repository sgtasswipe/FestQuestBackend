package com.example.festquestbackend.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.models.quests.SubQuest;
import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.models.users.QuestParticipant;
import com.example.festquestbackend.models.users.Role;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import com.example.festquestbackend.repositories.users.RoleRepository;

@Service
public class QuestService {
    private final QuestRepository questRepository;
    private final FestUserService festUserService;
    private final RoleRepository roleRepository;

    public QuestService(QuestRepository questRepository,
                        FestUserService festUserService,
                        RoleRepository roleRepository) {
        this.questRepository = questRepository;
        this.festUserService = festUserService;
        this.roleRepository = roleRepository;
    }

    public List<Quest> findAll() {
        return questRepository.findAll();
    }

    public Optional<Quest> findById(long id) {
        return questRepository.findById(id);
    }

    public Quest createQuest(Quest quest, Long userId) {
        try {
            FestUser user = festUserService.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Role creatorRole = roleRepository.findByName("CREATOR")
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName("CREATOR");
                        return roleRepository.save(newRole);
                    });

            QuestParticipant creator = new QuestParticipant();
            creator.setUser(user);
            creator.setRole(creatorRole);
            creator.setGoing(true);

            quest.setQuestParticipants(new ArrayList<>());
            quest.getQuestParticipants().add(creator);
            creator.setQuest(quest); // Important: Set both sides of bidirectional relationship

            Quest savedQuest = questRepository.save(quest);

            System.out.println("Created quest with ID: " + savedQuest.getId());
            System.out.println("Number of participants: " + savedQuest.getQuestParticipants().size());
            System.out.println("Creator role: " + creator.getRole().getName());

            return savedQuest;
        } catch (Exception e) {
            System.err.println("Error in createQuest: " + e.getMessage());
            throw new RuntimeException("Failed to create quest: " + e.getMessage(), e);
        }
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

    public List<Quest> findAllQuestsForUserByEmail(String email) {
        // Find the user by their email
        FestUser user = festUserService.findByEmail(email)
                .orElseThrow(() -> new NullPointerException("User not found with email: " + email));

        // Fetch quests where the user is a participant
        return questRepository.findDistinctByQuestParticipants_FestUserId(user.getId());
    }
}
