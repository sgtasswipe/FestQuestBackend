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
    Optional<Quest> findById(Long id);
    // JpaRepository eller CrudRepository??

    @Query("""
        SELECT DISTINCT q FROM Quest q 
        JOIN q.questParticipants qp 
        WHERE qp.user.id = :userId 
        AND qp.isGoing = true 
        ORDER BY q.startTime ASC
        """)
    List<Quest> findActiveQuestsByUserId(@Param("userId") Long userId);

    List<Quest> findDistinctByQuestParticipants_UserIdOrderByStartTimeAsc(Long userId);
}
