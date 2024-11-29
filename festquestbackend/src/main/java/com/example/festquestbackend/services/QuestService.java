package com.example.festquestbackend.services;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.repositories.QuestRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Service
public class QuestService {

    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public List<Quest> findAll() {

        Long userId = getLoggedInUserId();
        // todo sort quests before returning them
      //  return questRepository.findAllByUserId(userId);
        return questRepository.findDistinctByQuestParticipants_UserId(userId);
    }


    public Optional<Quest> findById(long id) {
        return questRepository.findById(id);
    }


    public Optional<Quest> save(Quest quest) {
        try {
            validateDates(quest);
            return Optional.of(questRepository.save(quest));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public void validateDates(Quest quest) {
        if (quest.getEndTime() != null && quest.getStartTime().isAfter(quest.getEndTime()))
            throw new IllegalArgumentException("End date must be before start date");

        if (quest.getStartTime().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Start time cannot be in the past");
    }

    public void updateQuest(Quest updatedQuest, Quest existingQuest) {
        try {
            validateDates(updatedQuest);
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
