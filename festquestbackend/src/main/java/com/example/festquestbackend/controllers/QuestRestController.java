package com.example.festquestbackend.controllers;

import java.util.*;

import com.example.festquestbackend.services.RoleService;
import com.example.festquestbackend.services.SubQuestService;
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

@RestController
@RequestMapping("/questboard")   // every endpoint will start with /questboard
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500", "http://localhost:63342", "http://127.0.0.1:5501"})
public class QuestRestController {
    private final QuestService questService;
    private final JwtUtil jwtUtil;
    private final FestUserService festUserService;
    private final RoleService roleService;

    public QuestRestController(QuestService questService, JwtUtil jwtUtil, FestUserService festUserService, RoleService roleService) {
        this.questService = questService;
        this.jwtUtil = jwtUtil;
        this.festUserService = festUserService;
        this.roleService = roleService;
    }

    @GetMapping("/all")  // GET /questboard/all instead of /questboard/questboard
    public List<Quest> findAllQuests() {
        return questService.findAll();
    }

    @GetMapping("/quest/{id}")
    public ResponseEntity<Quest> getQuestById(@PathVariable long id, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                        .filter(ignored -> roleService.validateAuthorization(authorizationHeader, id, RoleService.MEMBER))
                        .flatMap(ignored -> questService.findById(id))
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
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
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating quest: " + e.getMessage());
        }
    }

    @PutMapping("/quest/{id}")
    public ResponseEntity<Quest> updateQuest(@PathVariable long id, @RequestBody Quest updatedQuest, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                        .filter(ignored -> roleService.validateAuthorization(authorizationHeader, id, RoleService.CREATOR))
                        .flatMap(ignored -> questService.findById(id))
                        .flatMap(existingQuest -> questService.updateQuest(updatedQuest, existingQuest))
                        .map(isUpdated -> ResponseEntity.ok(updatedQuest))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @DeleteMapping("/quest/{id}")
    public ResponseEntity<Quest> deleteQuest(@PathVariable long id, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, id, RoleService.CREATOR))
                .flatMap(ignored -> questService.findById(id))
                .flatMap(questService::deleteQuest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/quests")
    public ResponseEntity<List<Quest>> getUserQuests(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtUtil.extractClaim(token, Claims::getSubject); // Extract 'sub' claim as email
        List<Quest> userQuests = questService.findAllQuestsForUserByEmail(email);

        return ResponseEntity.ok(userQuests.isEmpty() ? Collections.emptyList() : userQuests);
    }

    @PostMapping("/quest/{id}/generateShareToken")
    public ResponseEntity<Map<String, String>> generateShareToken(@PathVariable Long id) {
        Quest quest = questService.generateShareToken(id);
        Map<String, String> response = new HashMap<>();
        response.put("shareToken", quest.getShareToken());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/shared/{shareToken}")
    public ResponseEntity<Quest> getQuestByShareToken(@PathVariable String shareToken) {
        return questService.findByShareToken(shareToken)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
