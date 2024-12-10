package com.example.festquestbackend.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.services.FestUserService;
import com.example.festquestbackend.services.QuestService;
import com.example.festquestbackend.util.JwtUtil;

import io.jsonwebtoken.Claims;

// Controller
@RestController
@RequestMapping("/questboard")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500" , "http://localhost:63342"})
public class QuestRestController {
    private final QuestService questService;

    private final JwtUtil jwtUtil;
    private final FestUserService festUserService;

    public QuestRestController(QuestService questService, JwtUtil jwtUtil, FestUserService festUserService) {
        this.questService = questService;
        this.jwtUtil = jwtUtil;
        this.festUserService = festUserService;
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

    @PostMapping(value = "/quest", consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<?> createQuestFunc(
        @RequestBody Quest quest,
        @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            String email = jwtUtil.extractClaim(token, Claims::getSubject);
            FestUser user = festUserService.findByEmail(email).get();
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            Quest savedQuest = questService.createQuest(quest, user.getId());
            return ResponseEntity.ok(savedQuest);
        } catch (Exception e) {
            String errorMessage = "Error creating quest: " + e.getMessage();
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


    @GetMapping("/quests")
    public ResponseEntity<List<Quest>> getUserQuests(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtUtil.extractClaim(token, Claims::getSubject); // Extract 'sub' claim as email
        List<Quest> userQuests = questService.findAllForUserByEmail(email);

        if (userQuests.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(userQuests);
    }



    @GetMapping("/quests/{userId}")
    public ResponseEntity<List<Quest>> getQuestsForUser(@PathVariable Long userId) {
        List<Quest> quests = questService.findAllForUser(userId);
        return ResponseEntity.ok(quests);
    }
}
