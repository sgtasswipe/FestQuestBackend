package com.example.festquestbackend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.repositories.quests.QuestRepository;

@Service
public class QuestService {
    private final QuestRepository questRepository;
    private final UserService userService;
    public QuestService(QuestRepository questRepository, UserService userService) {
        this.questRepository = questRepository;
        this.userService = userService;
    }

    public List<Quest> findAll() {

       //  Long userId =  userService.getLoggedInUser();
        // todo sort quests before returning them
      //  return questRepository.findAllByUserId(userId);
      return questRepository.findDistinctByQuestParticipants_UserId(1L);  // hard-coded for now, will have to figure out how to retrieve user from session
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

    public void validateQuestDates(Quest quest) {
        if (quest.getEndTime() != null && quest.getStartTime().isAfter(quest.getEndTime()))
            throw new IllegalArgumentException("End date must be before start date");


        if (quest.getStartTime().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Start time cannot be in the past");
    }

    public void updateQuest(Quest updatedQuest, Quest existingQuest) {
        try {
            validateQuestDates(updatedQuest);
            BeanUtils.copyProperties(updatedQuest, existingQuest, "id"); //Spring metode der kopierer attributter
            questRepository.save(existingQuest);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteQuest(Quest quest) {
        questRepository.delete(quest);
    }
}
