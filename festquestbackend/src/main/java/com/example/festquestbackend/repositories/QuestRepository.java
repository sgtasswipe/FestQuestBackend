package com.example.festquestbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.festquestbackend.models.quests.Quest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    Optional<Quest> findQuestsById(Long id);
// JpaRepository eller CrudRepository??

    @Query("SELECT q FROM Quest q JOIN q.questParticipants qp WHERE qp.festUser.id = :userId")
    List<Quest> findAllQuestByUserId(@Param("userId") Long userId);

    List<Quest> findDistinctByQuestParticipants_FestUserId(Long festUserId);
    // This method find all quests from a given user id
}

