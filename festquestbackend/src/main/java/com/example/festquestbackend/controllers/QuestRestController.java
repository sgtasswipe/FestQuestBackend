package com.example.festquestbackend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.services.QuestService;

@RestController
@RequestMapping("/questboard")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestRestController {

    private final QuestService questService;

    public QuestRestController(QuestService questService) {
    this.questService = questService;
    }

    @GetMapping("/questboard")
    public List<Quest> getQuestboard() {
      return questService.findAll();
    }

    @GetMapping("/quest/{id}")
    public ResponseEntity<Quest> getQuest(@RequestParam long id) {

         /*Imperativ programming version:

        //if (questOptional.isPresent()) {
            return ResponseEntity.ok(questOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        */

        //Funktionel programming version:
        return questService.findById(id)
                .map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/create")
        public ResponseEntity<Quest> createQuest(@RequestBody Quest quest) {
        return questService.save(quest);
    }

    @PutMapping
    public ResponseEntity<Quest> updateQuest() {
    return null;
    }

    @DeleteMapping
    public ResponseEntity<Quest> deleteQuest() {
    return null;
    }




}



