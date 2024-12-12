package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.quests.SubQuest;
import com.example.festquestbackend.services.RoleService;
import com.example.festquestbackend.services.SubQuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quest/{questId}")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500" , "http://localhost:63342"})
public class SubQuestController {
    private final SubQuestService subQuestService;
    private final RoleService roleService;

    public SubQuestController(SubQuestService subQuestService, RoleService roleService) {
        this.subQuestService = subQuestService;
        this.roleService = roleService;
    }

    @GetMapping("/sub-quests")
    public ResponseEntity<List<SubQuest>> getSubQuestsByQuestId(@PathVariable Long questId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.MEMBER))
                .map(ignored -> {
                    List<SubQuest> subQuests = subQuestService.findSubQuestsByQuestId(questId);
                    return ResponseEntity.ok(subQuests);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/sub-quest/{subQuestId}")
    public ResponseEntity<SubQuest> getSubQuestById(@PathVariable Long questId, @PathVariable Long subQuestId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId,  RoleService.MEMBER))
                .map(ignored -> {
                    SubQuest subQuest = subQuestService.findById(subQuestId);
                    return ResponseEntity.ok(subQuest);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/sub-quest")
    public ResponseEntity<Object> createSubQuest(@PathVariable Long questId, @RequestBody SubQuest subQuest, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.ADMIN))
                .map(ignored -> subQuestService.createSubQuest(questId, subQuest))
                .map(isCreated -> isCreated ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PutMapping("/sub-quest/{subQuestId}")
    public ResponseEntity<Object> updateSubQuest(@PathVariable Long questId, @PathVariable Long subQuestId, @RequestBody SubQuest updatedSubQuest, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.ADMIN))
                .map(ignored -> subQuestService.updateSubQuest(subQuestId, updatedSubQuest))
                .map(isUpdated -> isUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @DeleteMapping("/sub-quest/{subQuestId}")
    public ResponseEntity<Object> deleteSubQuest(@PathVariable Long questId, @PathVariable Long subQuestId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.ADMIN))
                .map(ignored -> subQuestService.deleteSubQuest(subQuestId))
                .map(isDeleted -> isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}