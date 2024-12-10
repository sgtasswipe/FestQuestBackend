package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.quests.SubQuest;
import com.example.festquestbackend.services.FestUserService;
import com.example.festquestbackend.services.QuestParticipantService;
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
    private final FestUserService festUserService;
    private final QuestParticipantService questParticipantService;

    public SubQuestController(SubQuestService subQuestService, FestUserService festUserService, QuestParticipantService questParticipantService) {
        this.subQuestService = subQuestService;
        this.festUserService = festUserService;
        this.questParticipantService = questParticipantService;
    }

    @GetMapping("/sub-quests")
    public ResponseEntity<List<SubQuest>> getSubQuestsByQuestId(@PathVariable long questId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .map(festUserService::getFestUserByAuthHeader)
                .filter(festUser -> questParticipantService.checkIfFestUserHasAuthority(questId, festUser))
                .map(ignored -> {
                    List<SubQuest> subQuests = subQuestService.findSubQuestsByQuestId(questId);
                    return ResponseEntity.ok(subQuests);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping(value = "/sub-quest", consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<Object> createSubQuest(@PathVariable long questId, @RequestBody SubQuest subQuest, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .map(festUserService::getFestUserByAuthHeader)
                .filter(festUser -> questParticipantService.checkIfFestUserHasAuthority(questId, festUser))
                .map(ignored -> subQuestService.createSubQuest(questId, subQuest))
                .map(isCreated -> isCreated ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PutMapping("/sub-quest/{subQuestId}")
    public ResponseEntity<Object> updateSubQuest(@PathVariable long questId, @PathVariable long subQuestId, @RequestBody SubQuest updatedSubQuest, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .map(festUserService::getFestUserByAuthHeader)
                .filter(festUser -> questParticipantService.checkIfFestUserHasAuthority(questId, festUser))
                .map(ignored -> subQuestService.updateSubQuest(subQuestId, updatedSubQuest))
                .map(isUpdated -> isUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @DeleteMapping("/sub-quest/{subQuestId}")
    public ResponseEntity<Object> deleteSubQuest(@PathVariable long questId, @PathVariable long subQuestId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .map(festUserService::getFestUserByAuthHeader)
                .filter(festUser -> questParticipantService.checkIfFestUserIsCreator(questId, festUser))
                .map(ignored -> subQuestService.deleteSubQuest(subQuestId))
                .map(isDeleted -> isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}