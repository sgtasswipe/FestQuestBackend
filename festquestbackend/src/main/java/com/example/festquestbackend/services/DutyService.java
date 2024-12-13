package com.example.festquestbackend.services;

import com.example.festquestbackend.models.quests.Duty;
import com.example.festquestbackend.repositories.quests.DutyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DutyService {
    private final DutyRepository dutyRepository;
    private final SubQuestService subQuestService;

    public DutyService(DutyRepository dutyRepository, SubQuestService subQuestService) {
        this.dutyRepository = dutyRepository;
        this.subQuestService = subQuestService;
    }

    public Optional<List<Duty>> findDuties(long subQuestId, long questId) {
        return subQuestService.findByIdAndQuestId(subQuestId, questId)
                .flatMap(ignored -> dutyRepository.findDutiesBySubQuestId(subQuestId));
    }

    // Find a Duty by its ID, associated SubQuest ID, and Quest ID, ensuring the hierarchy is valid
    public Optional<Duty> findByIdAndSubQuestIdAndQuestId(long dutyId, long subQuestId, long questId) {
        return subQuestService.findByIdAndQuestId(subQuestId, questId)
                .flatMap(ignored -> dutyRepository.findByIdAndSubQuestId(dutyId, subQuestId));
    }

    public boolean createDuty(long subQuestId, long questId, Duty duty) {
        return subQuestService.findByIdAndQuestId(subQuestId, questId)
                .map(subQuest -> {
                    duty.setSubQuest(subQuest);
                    dutyRepository.save(duty);
                    return true;
                })
                .orElse(false);
    }

    public boolean updateDuty(long dutyId, long subQuestId, long questId, Duty updatedDuty) {
        return findByIdAndSubQuestIdAndQuestId(dutyId, subQuestId, questId)
                .map(existingDuty -> {
                    BeanUtils.copyProperties(updatedDuty, existingDuty, "id", "subQuest");
                    existingDuty.setTitle(updatedDuty.getTitle());
                    existingDuty.setPrice(updatedDuty.getPrice());
                    dutyRepository.save(existingDuty);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteDuty(long dutyId, long subQuestId, long questId) {
        return findByIdAndSubQuestIdAndQuestId(dutyId, subQuestId, questId)
                .map(ignored -> {
                    dutyRepository.deleteById(dutyId);
                    return true;
                })
                .orElse(false);
    }
}