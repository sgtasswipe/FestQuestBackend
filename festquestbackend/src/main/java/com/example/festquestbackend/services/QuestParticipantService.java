package com.example.festquestbackend.services;

import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.repositories.users.QuestParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestParticipantService {
    private final QuestParticipantRepository questParticipantRepository;
    private final QuestService questService;

    public QuestParticipantService(QuestParticipantRepository questParticipantRepository, QuestService questService) {
        this.questParticipantRepository = questParticipantRepository;
        this.questService = questService;
    }

    public boolean checkIfFestUserHasAuthority(long questId, FestUser festUser) {
        return questService.findById(questId)
                .map(quest -> quest.getQuestParticipants().stream()
                        .filter(participant -> participant.getUser().getId() == festUser.getId())
                        .findFirst()
                        .map(questParticipant -> {
                            String questParticipantRole = questParticipant.getRole().getName();
                            return questParticipantRole.equals("CREATOR") || questParticipantRole.equals("ADMIN") || questParticipantRole.equals("MEMBER");
                        })
                        .orElse(false) // If the user doesn't have authority
                )
                .orElse(false);
    }

    public boolean checkIfFestUserHasAdminAuthority(long questId, FestUser festUser) {
        return questService.findById(questId)
                .map(quest -> quest.getQuestParticipants().stream()
                        .filter(participant -> participant.getUser().getId() == festUser.getId())
                        .findFirst()
                        .map(questParticipant -> {
                            String questParticipantRole = questParticipant.getRole().getName();
                            return questParticipantRole.equals("CREATOR") || questParticipantRole.equals("ADMIN");
                        })
                        .orElse(false) // If the user doesn't have authority
                )
                .orElse(false);
    }

    public boolean checkIfFestUserIsCreator(long questId, FestUser festUser) {
        return questService.findById(questId)
                .map(quest -> quest.getQuestParticipants().stream()
                        .filter(participant -> participant.getUser().getId() == festUser.getId())
                        .findFirst()
                        .map(questParticipant -> {
                            String questParticipantRole = questParticipant.getRole().getName();
                            return questParticipantRole.equals("CREATOR");
                        })
                        .orElse(false) // If the user doesn't have authority
                )
                .orElse(false);
    }
}
