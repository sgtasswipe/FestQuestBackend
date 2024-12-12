package com.example.festquestbackend.repositories.quests;

import org.springframework.stereotype.Repository;
import com.example.festquestbackend.models.quests.SubQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface SubQuestRepository extends JpaRepository<SubQuest,Long > {
    List<SubQuest> findSubQuestsByQuestId(long questId);
}