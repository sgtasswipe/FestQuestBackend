package com.example.festquestbackend.services;

import com.example.festquestbackend.models.quests.SubQuest;
import com.example.festquestbackend.repositories.quests.SubQuestRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubQuestService {
    private final SubQuestRepository subQuestRepository;
    private final QuestService questService;

    public SubQuestService(SubQuestRepository subQuestRepository, QuestService questService) {
        this.subQuestRepository = subQuestRepository;
        this.questService = questService;
    }

    public Optional<List<SubQuest>> findSubQuests(long questId) {
        return subQuestRepository.findSubQuestsByQuestId(questId);
    }

    public Optional<SubQuest> findByIdAndQuestId(long subQuestId, long questId) {
        return subQuestRepository.findByIdAndQuestId(subQuestId, questId);
    }

    public boolean createSubQuest(long questId, SubQuest subQuest) {
        return questService.findById(questId)
                .map(quest -> {
                    subQuest.setQuest(quest);
                    subQuestRepository.save(subQuest);
                    return true;
                })
                .orElse(false);
    }

    public boolean updateSubQuest(long subQuestId, long questId, SubQuest updatedSubQuest) {
        return subQuestRepository.findByIdAndQuestId(subQuestId, questId)
                .map(existingSubquest -> {
                    BeanUtils.copyProperties(updatedSubQuest, existingSubquest, "id", "quest", "dutyList");
                    existingSubquest.setTitle(updatedSubQuest.getTitle());
                    existingSubquest.setBudget(updatedSubQuest.getBudget());
                    subQuestRepository.save(existingSubquest);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteSubQuest(long subQuestId, long questId) {
        return subQuestRepository.findByIdAndQuestId(subQuestId, questId)
                .map(ignored -> {
                    subQuestRepository.deleteById(subQuestId);
                    return true;
                })
                .orElse(false);
    }
}