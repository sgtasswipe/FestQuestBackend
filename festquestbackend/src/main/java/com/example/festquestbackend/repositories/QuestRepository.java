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


        @Query("SELECT q FROM Quest q JOIN q.questParticipants qp WHERE qp.user.id = :userId")
        List<Quest> findAllByUserId(@Param("userId") Long userId);
    List<Quest> findDistinctByQuestParticipants_UserId(Long userId);  // Methods does the same, depends on if we want to use JPQL

}
