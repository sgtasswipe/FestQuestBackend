package com.example.festquestbackend.services;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestService {

    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public List<Quest> findAll() {
        return questRepository.findAll();
    }

    public Optional<Quest> findById(long id) {
        return questRepository.findById(id);
    }

    public ResponseEntity<Quest> save(Quest quest) {
  // move responseentity to controller
        // add logic to prevent date in past as start time
        // prevent end time to be before starttime
        try {
            Quest savedQuest = questRepository.save(quest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuest);
        }
        catch (Exception e) {  //todo global exception handling?
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




}
