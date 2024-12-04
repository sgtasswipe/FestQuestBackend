package com.example.festquestbackend.services;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.models.users.QuestParticipant;
import com.example.festquestbackend.repositories.QuestRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


import com.example.festquestbackend.repositories.quests.QuestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

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

        // Get all quests by a User ID sorted by Start Time Ascending
        List<Quest> quests = questRepository.findDistinctByQuestParticipants_UserIdOrderByStartTimeAsc(1L);

        // Filter all quests so that only the specified User remains as the sole participant in each quest
        return quests.stream()
            // Return the value of the first element in the list
            .peek(quest -> {
                List<QuestParticipant> filteredParticipants = quest.getQuestParticipants()
                        .stream()
                        .filter(qp -> qp.getUser().getId() == 1L) // HARD CODED FOR NOW
                        .collect(Collectors.toList());

                // Set the filtered participants back to the quest
                quest.setQuestParticipants(filteredParticipants);
            })
            .collect(Collectors.toList());

        // Does the same as the above - Directly sets the new list of Quest Participants (Which is more readable?)
        /*return quests.stream()
                // Return the value of the first element in the list
                .peek(quest -> {
                    quest.setQuestParticipants(
                            quest.getQuestParticipants()
                                    .stream()
                                    .filter(qp -> qp.getUser().getId() == 1L) // HARD CODED FOR NOW
                                    .collect(Collectors.toList()));

                })
                .collect(Collectors.toList());
         */
    }


    public Optional<Quest> findById(long id) {
        return questRepository.findById(id);
    }




    public Optional<Quest> save(Quest quest) {
  // move responseentity to controller
        // add logic to prevent date in past as start time
        // prevent end time to be before starttime

        try {
            validateQuestDates(quest);
            return Optional.of(questRepository.save(quest));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
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
