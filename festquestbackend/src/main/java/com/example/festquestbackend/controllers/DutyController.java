package com.example.festquestbackend.controllers;

import com.example.festquestbackend.models.quests.Duty;
import com.example.festquestbackend.services.DutyService;
import com.example.festquestbackend.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quest/{questId}/sub-quest/{subQuestId}")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500" , "http://localhost:63342"})
public class DutyController {
    private final DutyService dutyService;
    private final RoleService roleService;

    public DutyController(DutyService dutyService, RoleService roleService) {
        this.dutyService = dutyService;
        this.roleService = roleService;
    }

    @GetMapping("/duties")
    public ResponseEntity<List<Duty>> getDutiesBySubQuestId(@PathVariable long questId, @PathVariable long subQuestId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.MEMBER))
                .flatMap(ignored -> dutyService.findDuties(subQuestId, questId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());  // Return UNAUTHORIZED if SubQuest doesn't exist or user is unauthorized
    }

    @GetMapping("/duty/{dutyId}")
    public ResponseEntity<Duty> getDutyById(@PathVariable long questId, @PathVariable long subQuestId, @PathVariable long dutyId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.MEMBER))
                .flatMap(ignored -> dutyService.findByIdAndSubQuestIdAndQuestId(dutyId, subQuestId, questId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/duty")
    public ResponseEntity<Object> createDuty(@PathVariable long questId, @PathVariable long subQuestId, @RequestBody Duty duty, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.MEMBER))
                .map(ignored -> dutyService.createDuty(subQuestId, questId, duty))
                .map(isCreated -> isCreated ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PutMapping("/duty/{dutyId}")
    public ResponseEntity<Object> updateDuty(@PathVariable long questId, @PathVariable long subQuestId, @PathVariable long dutyId, @RequestBody Duty updatedDuty, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.MEMBER))
                .map(ignored -> dutyService.updateDuty(dutyId, subQuestId, questId, updatedDuty))
                .map(isUpdated -> isUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @DeleteMapping("/duty/{dutyId}")
    public ResponseEntity<Object> deleteDuty(@PathVariable long questId, @PathVariable long subQuestId, @PathVariable long dutyId, @RequestHeader("Authorization") String authorizationHeader) {
        return Optional.of(authorizationHeader)
                .filter(ignored -> roleService.validateAuthorization(authorizationHeader, questId, RoleService.MEMBER))
                .map(ignored -> dutyService.deleteDuty(dutyId, subQuestId, questId))
                .map(isDeleted -> isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}