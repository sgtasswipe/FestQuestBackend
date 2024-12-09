package com.example.festquestbackend.controllers;

import java.util.List;
import java.util.Optional;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import com.example.festquestbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.services.QuestService;

import java.security.Principal;

// Controller
@RestController
@RequestMapping("/questboard")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500" , "http://localhost:63342"})
public class QuestRestController {
    private final QuestService questService;

    private final JwtUtil jwtUtil;
    public QuestRestController(QuestService questService, JwtUtil jwtUtil) {
    this.questService = questService;
    this.jwtUtil = jwtUtil;
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


    @GetMapping("/quests")
    public ResponseEntity<List<Quest>> getUserQuests(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtUtil.extractClaim(token, Claims::getSubject); // Extract 'sub' claim as email
        List<Quest> userQuests = questService.findAllForUserByEmail(email);

        if (userQuests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userQuests);
    }



    @GetMapping("/quests/{userId}")
    public ResponseEntity<List<Quest>> getQuestsForUser(@PathVariable Long userId) {
        List<Quest> quests = questService.findAllForUser(userId);
        return ResponseEntity.ok(quests);
    }
}
