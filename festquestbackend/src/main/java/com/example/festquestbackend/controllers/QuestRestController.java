package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.quests.Quest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.festquestbackend.services.QuestService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

// Controller
@RestController
@RequestMapping("/questboard")

public class QuestRestController {
private final QuestService questService;
public QuestRestController (QuestService questService) {
    this.questService = questService;
}

    @GetMapping("/quests")
    public List<Quest> getQuestboard() {
      return questService.findAll();
    }



    @GetMapping("/quest/{id}")
    public ResponseEntity<Quest> getQuest(@RequestParam long id) {
        return questService.findById(id)
                .map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/quest")
    public ResponseEntity<Quest> createQuestFunc ( @RequestBody Quest quest) {
    return questService.save(quest).map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // spring security test method req authentication
    @PostMapping("/testsecure")
    @PreAuthorize("hasRole('USER')") // Spring Security annotation
    public ResponseEntity<Quest> createQuest(
            @RequestBody Quest quest,
            Principal principal
    ) {
        // principal.getName() gives authenticated user's email
        String userEmail = principal.getName();
        Quest createdQuest = questService.save(quest).get();
        return ResponseEntity.ok(createdQuest);
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
    public ResponseEntity<Quest> deleteQuest(@PathVariable long id) {
        return questService.findById(id)
                .map(quest -> {
                    questService.deleteQuest(quest);
                    return ResponseEntity.ok(quest);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}



