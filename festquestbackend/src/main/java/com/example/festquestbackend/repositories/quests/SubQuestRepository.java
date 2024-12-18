package com.example.festquestbackend.repositories.quests;

import org.springframework.stereotype.Repository;
import com.example.festquestbackend.models.quests.SubQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubQuestRepository extends JpaRepository<SubQuest,Long > {
    Optional<List<SubQuest>> findSubQuestsByQuestId(long questId);
    Optional<SubQuest> findByIdAndQuestId(long subQuestId, long questId);
}