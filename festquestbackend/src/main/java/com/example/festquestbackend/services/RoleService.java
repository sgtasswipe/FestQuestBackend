package com.example.festquestbackend.services;

import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.models.users.QuestParticipant;
import com.example.festquestbackend.models.users.Role;
import com.example.festquestbackend.repositories.quests.FestRoleRepository;
import jakarta.annotation.PostConstruct;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    public static Role CREATOR;
    public static Role ADMIN;
    public static Role MEMBER;

    private final FestRoleRepository festRoleRepository;
    private final FestUserService festUserService;
    private final QuestService questService;

    public RoleService(FestRoleRepository festRoleRepository, FestUserService festUserService, QuestService questService) {
        this.festRoleRepository = festRoleRepository;
        this.festUserService = festUserService;
        this.questService = questService;
    }

    @PostConstruct
    public void initializeRoles() {
        CREATOR = festRoleRepository.findById(1L)
                .orElseThrow(() -> new ObjectNotFoundException("Creator", 1L));
        ADMIN = festRoleRepository.findById(2L)
                .orElseThrow(() -> new ObjectNotFoundException("Admin", 2L));
        MEMBER = festRoleRepository.findById(3L)
                .orElseThrow(() -> new ObjectNotFoundException("Member", 3L));
    }

    private boolean isAuthorizedForRole(Role clearanceLvl, Role festUserRole) {
        return clearanceLvl.getId() >= festUserRole.getId();
    }

    private Optional<Role> getRoleByQuestIdAndFestUser(long questId, FestUser festUser) {
        return questService.findById(questId)
                .flatMap(quest -> quest.getQuestParticipants().stream()
                        .filter(participant -> participant.getUser().getId() == festUser.getId())
                        .findFirst()
                        .map(QuestParticipant::getRole));
    }

    public boolean validateAuthorization(String authorizationHeader, Long questId, Role requiredRole) {
        return Optional.ofNullable(festUserService.getFestUserByAuthHeader(authorizationHeader))
                .flatMap(festUser -> getRoleByQuestIdAndFestUser(questId, festUser))
                .map(userRole -> isAuthorizedForRole(requiredRole, userRole))
                .orElse(false);
    }
}
