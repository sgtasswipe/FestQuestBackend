package com.example.festquestbackend.repositories.quests;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.festquestbackend.models.quests.Quest;
@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    Optional<Quest> findQuestsById(Long id);
// JpaRepository eller CrudRepository??


    @Query("SELECT q FROM Quest q JOIN q.questParticipants qp WHERE qp.user.id = :userId")
    List<Quest> findAllQuestByUserId(@Param("userId") Long userId);
    List<Quest> findDistinctByQuestParticipants_UserId(Long userId);  // Methods does the same, depends on if we want to use JPQL
    // This method find all quests from a given user id

    // Retrieves distinct quests for a given user ID, sorted by start time in ascending order
    List<Quest> findDistinctByQuestParticipants_UserIdOrderByStartTimeAsc(Long userId);
}

