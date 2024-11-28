package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.quests.Quest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.festquestbackend.services.QuestService;
import java.util.List;

@RestController
@RequestMapping("questboard´")

public class QuestRestController {
private final QuestService questService;
public QuestRestController (QuestService questService) {
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


    @PostMapping ("/create")
    public ResponseEntity<Quest> createQuest(@RequestBody Quest quest) {
       questService.save(quest);
       //todo fix
        return null;
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



