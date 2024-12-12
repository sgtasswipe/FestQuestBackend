package com.example.festquestbackend.services;

import com.example.festquestbackend.models.quests.SubQuest;
import com.example.festquestbackend.repositories.quests.SubQuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubQuestService {
    private final SubQuestRepository subQuestRepository;
    private final QuestService questService;

    public SubQuestService(SubQuestRepository subQuestRepository, QuestService questService) {
        this.subQuestRepository = subQuestRepository;
        this.questService = questService;
    }

    public List<SubQuest> findSubQuestsByQuestId(long questId) {
        return subQuestRepository.findSubQuestsByQuestId(questId);
    }

    public boolean createSubQuest(Long questId, SubQuest subQuest) {
        return questService.findById(questId)
                .map(quest -> {
                    subQuest.setQuest(quest);
                    subQuestRepository.save(subQuest);
                    return true;
                })
                .orElse(false);
    }

    public boolean updateSubQuest(long subQuestId, SubQuest updatedSubQuest) {
        return subQuestRepository.findById(subQuestId)
                .map(subQuest -> {
                    subQuestRepository.save(updatedSubQuest);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteSubQuest(long subQuestId) {
        return subQuestRepository.findById(subQuestId)
                .map(ignored -> {
                    subQuestRepository.deleteById(subQuestId);
                    return true;
                })
                .orElse(false);
    }
}