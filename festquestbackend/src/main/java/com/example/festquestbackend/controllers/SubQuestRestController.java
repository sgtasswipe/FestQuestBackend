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
public class SubQuestRestController {
    private final SubQuestService subQuestService;
    private final RoleService roleService;

    public SubQuestRestController(SubQuestService subQuestService, RoleService roleService) {
        this.subQuestService = subQuestService;
        this.roleService = roleService;
    }

    @GetMapping("/sub-quests")
    public ResponseEntity<List<SubQuest>> getSubQuestsByQuestId(@PathVariable long questId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.MEMBER))
                .flatMap(ignored -> subQuestService.findSubQuests(questId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/sub-quest/{subQuestId}")
    public ResponseEntity<SubQuest> getSubQuestById(@PathVariable long questId, @PathVariable long subQuestId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId,  RoleService.MEMBER))
                .flatMap(ignored -> subQuestService.findByIdAndQuestId(subQuestId, questId)) // Unwraps Optional, in this case Optional<SubQuest>
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/sub-quest")
    public ResponseEntity<Object> createSubQuest(@PathVariable long questId, @RequestBody SubQuest subQuest, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.ADMIN))
                .map(ignored -> subQuestService.createSubQuest(questId, subQuest))
                .map(isCreated -> isCreated ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PutMapping("/sub-quest/{subQuestId}")
    public ResponseEntity<Object> updateSubQuest(@PathVariable long questId, @PathVariable long subQuestId, @RequestBody SubQuest updatedSubQuest, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.ADMIN))
                .flatMap(ignored -> subQuestService.findByIdAndQuestId(subQuestId, questId)) // Check if the sub quest exists
                .map(ignored -> subQuestService.updateSubQuest(subQuestId, questId, updatedSubQuest)) // If it does, update sub quest
                .map(isUpdated -> isUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @DeleteMapping("/sub-quest/{subQuestId}")
    public ResponseEntity<Object> deleteSubQuest(@PathVariable long questId, @PathVariable long subQuestId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.ADMIN))
                .map(ignored -> subQuestService.deleteSubQuest(subQuestId, questId))
                .map(isDeleted -> isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}