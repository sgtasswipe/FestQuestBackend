package com.example.festquestbackend.repositories.quests;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.festquestbackend.models.quests.Quest;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    Optional<Quest> findById(Long id);

    // Finds active quests by Fest user ID
    List<Quest> findDistinctByQuestParticipants_FestUserId(Long festUserId);

    Optional<Quest> findByShareToken(String shareToken);
}
