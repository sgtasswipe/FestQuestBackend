package com.example.festquestbackend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.services.QuestService;

// Controller
@RestController
@RequestMapping("/questboard")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"}, allowCredentials = "true")
public class QuestRestController {
    private final QuestService questService;

    @Autowired
    public QuestRestController(QuestService questService) {
    this.questService = questService;
    }

    @GetMapping("/questboard")
    public List<Quest> getQuestboard() {
      return questService.findAll();
    }


    @GetMapping("/quest/{id}")
    public ResponseEntity<Quest> getQuest(@PathVariable long id) {
        return questService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/quest")
    public ResponseEntity<?> createQuestFunc(@RequestBody Quest quest) {
        try {
            System.out.println("Received quest data: " + quest);
            Optional<Quest> savedQuest = questService.save(quest);
            return savedQuest
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            String errorMessage = "Error creating quest: " + e.getMessage();
            System.err.println(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @PutMapping ("/quest/{id}")
    public ResponseEntity<Quest> updateQuest(@PathVariable long id, @RequestBody Quest updatedQuest) {
        return questService.findById(id)
                .map(existingQuest -> {
                    questService.updateQuest(updatedQuest, existingQuest);
                    return ResponseEntity.ok(updatedQuest);
                }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/quest/{id}")
    public ResponseEntity<?> deleteQuest(@PathVariable long id) {
        try {
            return questService.findById(id)
                .map(quest -> {
                    questService.deleteQuest(quest);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting quest: " + e.getMessage());
        }
    }

    @GetMapping("/quests/{userId}")
    public ResponseEntity<List<Quest>> getQuestsForUser(@PathVariable Long userId) {
        List<Quest> quests = questService.findAllForUser(userId);
        return ResponseEntity.ok(quests);
    }
}
